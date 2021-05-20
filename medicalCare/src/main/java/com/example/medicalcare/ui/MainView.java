package com.example.medicalcare.ui;

import com.example.medicalcare.backend.entity.Hospital;
import com.example.medicalcare.backend.entity.Patient;
import com.example.medicalcare.backend.service.HospitalService;
import com.example.medicalcare.backend.service.PatientService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.stereotype.Component;

@Component
@Route("")
@CssImport("./styles/shared-styles.css")
@PageTitle("Data")
public class MainView extends VerticalLayout {
    Grid<Patient> grid = new Grid<>(Patient.class);
    TextField filterText = new TextField();
    PatientService patientService;
    PatientForm form;

    public MainView(PatientService patientService,
                    HospitalService hospitalService) {
        this.patientService = patientService;
        addClassName("data-grid");
        setSizeFull();
        configureGrid();

        form = new PatientForm(hospitalService.findAll());
        form.addListener(PatientForm.SaveEvent.class, this::savePatient);
        form.addListener(PatientForm.DeleteEvent.class, this::deleteContact);
        form.addListener(PatientForm.CloseEvent.class, e -> closeEditor());

        Div content = new Div(grid, form);
        content.addClassName("content");
        content.setSizeFull();

        add(getToolbar(), content);
        updateList();
        closeEditor();
    }

    private void savePatient(PatientForm.SaveEvent event) {
        patientService.save(event.getPatient());
        updateList();
        closeEditor();
    }

    private void deleteContact(PatientForm.DeleteEvent event) {
        patientService.delete(event.getPatient());
        updateList();
        closeEditor();
    }

    private void configureFilter() {
        filterText.setPlaceholder("Find by name...");
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.LAZY);
        filterText.addValueChangeListener(e -> updateList());
    }

    private HorizontalLayout getToolbar() {
        filterText.setPlaceholder("Filter by name...");
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.LAZY);
        filterText.addValueChangeListener(e -> updateList());

        Button addContactButton = new Button("Add patient");
        addContactButton.addClickListener(click -> addContact());
        Anchor logout = new Anchor("logout", "Log out");

        HorizontalLayout toolbar = new HorizontalLayout(filterText, addContactButton, logout);
        toolbar.addClassName("toolbar");
        //toolbar.expand(addContactButton);
        toolbar.setDefaultVerticalComponentAlignment(Alignment.CENTER);
        toolbar.setWidth("100%");
        return toolbar;
    }

    void addContact() {
        grid.asSingleSelect().clear();
        editPatient(new Patient());
    }

    private void updateList() {
        grid.setItems(patientService.findAll(filterText.getValue()));
    }

    private void configureGrid() {
        grid.addClassName("patient-grid");
        grid.setSizeFull();
        grid.removeColumnByKey("hospital");
        grid.setColumns("firstName", "lastName", "email", "condition");
        grid.addColumn(contact -> {
            Hospital hospital = contact.getHospital();
            return hospital == null ? "-": hospital.getName();
        }).setHeader("Hospital");
        grid.getColumns().forEach(col -> col.setAutoWidth(true));
        grid.asSingleSelect().addValueChangeListener(event ->
                editPatient(event.getValue()));
    }

    public void editPatient(Patient patient) {
        if (patient == null) {
            closeEditor();
        } else {
            form.setPatient(patient);
            form.setVisible(true);
            addClassName("editing");
        }
    }

    private void closeEditor() {
        form.setPatient(null);
        form.setVisible(false);
        removeClassName("editing");
    }
}
