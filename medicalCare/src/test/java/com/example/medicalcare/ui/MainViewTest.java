package com.example.medicalcare.ui;

import com.example.medicalcare.backend.entity.Patient;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.data.provider.ListDataProvider;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MainViewTest {

    @Autowired
    private MainView mainView;

    @Test
    public void formShownWhenContactSelected() {
        Grid<Patient> grid = mainView.grid;
        Patient firstContact = getFirstItem(grid);

        PatientForm form = mainView.form;

        Assert.assertFalse(form.isVisible());
        grid.asSingleSelect().setValue(firstContact);
        Assert.assertTrue(form.isVisible());
        Assert.assertEquals(firstContact.getFirstName(), form.firstName.getValue());
    }
    private Patient getFirstItem(Grid<Patient> grid) {
        return( (ListDataProvider<Patient>) grid.getDataProvider()).getItems().iterator().next();
    }
}