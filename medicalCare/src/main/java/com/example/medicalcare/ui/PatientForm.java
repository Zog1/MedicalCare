package com.example.medicalcare.ui;

import com.example.medicalcare.backend.entity.Hospital;
import com.example.medicalcare.backend.entity.Patient;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.shared.Registration;
import com.example.medicalcare.ui.PatientForm;

import java.util.List;

public class PatientForm extends FormLayout {

        TextField firstName = new TextField("First name");
        TextField lastName = new TextField("Last name");
        EmailField email = new EmailField("Email");
        ComboBox<Patient.Condition> condition = new ComboBox<>("Condition");
        ComboBox<Hospital> hospital = new ComboBox<>("Hospital");

        Button save = new Button("Save");
        Button delete = new Button("Delete");
        Button close = new Button("Cancel");

        Binder<Patient> binder = new Binder<>(Patient.class);

        public PatientForm(List<Hospital> companies) {
            addClassName("patient-form");

            binder.bindInstanceFields(this);
            condition.setItems(Patient.Condition.values());
            hospital.setItems(companies);
            hospital.setItemLabelGenerator(Hospital::getName);

            add(firstName,
                    lastName,
                    email,
                    hospital,
                    condition,
                    createButtonsLayout());
        }

        public void setPatient(Patient patient){
            binder.setBean(patient);
        }

        private Component createButtonsLayout() {
            save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
            delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
            close.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

            save.addClickListener(event -> validateAndSave());
            delete.addClickListener(event -> fireEvent(new DeleteEvent(this, binder.getBean())));
            close.addClickListener(event -> fireEvent(new CloseEvent(this)));

            save.addClickShortcut(Key.ENTER);
            close.addClickShortcut(Key.ESCAPE);
            HorizontalLayout horizontalLayout = new HorizontalLayout(save);

            binder.addStatusChangeListener(e -> save.setEnabled(binder.isValid()));
            binder.addStatusChangeListener(event -> save.setEnabled(binder.isValid()));

            return new HorizontalLayout(save, delete, close);
        }

        private void validateAndSave() {
            try {
                binder.writeBean(binder.getBean());
                fireEvent(new SaveEvent(this, binder.getBean()));
            } catch (ValidationException e) {
                e.printStackTrace();
            }
        }

        public static abstract class PatientFormEvent extends ComponentEvent<PatientForm> {
            private Patient patient;

            protected PatientFormEvent(PatientForm source, Patient patient) {
                super(source, false);
                this.patient = patient;
            }

            public Patient getPatient() {
                return patient;
            }
        }

        public static class SaveEvent extends PatientFormEvent {
            SaveEvent(PatientForm source, Patient patient) {
                super(source, patient);
            }
        }

        public static class DeleteEvent extends PatientFormEvent {
            DeleteEvent(PatientForm source, Patient patient) {
                super(source, patient);
            }

        }

        public static class CloseEvent extends PatientFormEvent {
            CloseEvent(PatientForm source) {
                super(source, null);
            }
        }

        public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType,
                                                                      ComponentEventListener<T> listener) {
            return getEventBus().addListener(eventType, listener);
        }
}
