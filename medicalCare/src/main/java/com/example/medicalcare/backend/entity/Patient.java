package com.example.medicalcare.backend.entity;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;


@Entity
public class Patient extends AbstractEntity {
    public enum Condition {
        Shock, SevereMechanicalTraumaWithMassiveBloodLoss,
        ComplicationDuringSurgeryAndAnesthesia,
        SevereDisordersOfTheCardiovascularSystem,
        AcuteRespiratoryFailure,
        ComatoseState
    }

    @NotNull
    @NotEmpty
    private String firstName = "";

    @NotNull
    @NotEmpty
    private String lastName = "";

    @ManyToOne
    @JoinColumn(name = "hospital_id")
    private Hospital hospital;

    @Enumerated(EnumType.STRING)
    @NotNull
    private Patient.Condition condition;

    @Email
    @NotNull
    @NotEmpty
    private String email = "";

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Condition getCondition() {
        return condition;
    }

    public void setCondition(Condition status) { this.condition = status; }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setHospital(Hospital company) {
        this.hospital = company;
    }

    public Hospital getHospital() {
        return hospital;
    }

    @Override
    public String toString() {
        return firstName + " " + lastName;
    }
}
