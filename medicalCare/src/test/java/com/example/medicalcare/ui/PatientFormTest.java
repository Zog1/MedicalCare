package com.example.medicalcare.ui;

import com.example.medicalcare.backend.entity.Hospital;
import com.example.medicalcare.backend.entity.Patient;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class PatientFormTest {
    private List<Hospital> companies;
    private Patient user;
    private Hospital hospital1;
    private Hospital hospital2;

    @Before
    public void setupData() {
        companies = new ArrayList<>();
        hospital1 = new Hospital("Hospital Tets1");
        hospital2 = new Hospital("Hospital Tets2");
        companies.add(hospital1);
        companies.add(hospital2);

        user = new Patient();
        user.setFirstName("Marc");
        user.setLastName("Usher");
        user.setEmail("marc.usher@gmail.com");
        user.setCondition(Patient.Condition.ComatoseState);
        user.setHospital(hospital1);
    }

    @Test
    public void formFieldsPopulated() {
        PatientForm form = new PatientForm(companies);
        form.setPatient(user);
        Assert.assertEquals("Marc", form.firstName.getValue());
        Assert.assertEquals("Usher", form.lastName.getValue());
        Assert.assertEquals("marc.usher@gmail.com", form.email.getValue());
        Assert.assertEquals(hospital1, form.hospital.getValue());
        Assert.assertEquals(Patient.Condition.ComatoseState, form.condition.getValue());
    }
}


