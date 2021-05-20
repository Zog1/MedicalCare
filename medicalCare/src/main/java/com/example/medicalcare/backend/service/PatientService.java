package com.example.medicalcare.backend.service;

import com.example.medicalcare.backend.entity.Hospital;
import com.example.medicalcare.backend.entity.Patient;
import com.example.medicalcare.backend.repository.HospitalRepository;
import com.example.medicalcare.backend.repository.PatientRepository;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class PatientService {
    private static final Logger LOGGER = Logger.getLogger(PatientService.class.getName());
    private PatientRepository patientRepository;
    private HospitalRepository hospitalRepository;

    public PatientService(PatientRepository patientRepository,
                          HospitalRepository hospitalRepository) {
        this.patientRepository = patientRepository;
        this.hospitalRepository = hospitalRepository;
    }

    public List<Patient> findAll() {
        return patientRepository.findAll();
    }

    public List<Patient> findAll(String filterText)
    {
        if (filterText == null || filterText.isEmpty()){
            return patientRepository.findAll();
        }else{
            return patientRepository.search(filterText);
        }
    }

    public long count() {
        return patientRepository.count();
    }

    public void delete(Patient patient) {
        patientRepository.delete(patient);
    }

    public void save(Patient patient) {
        if (patient == null) {
            LOGGER.log(Level.SEVERE,
                    "Patient is null. Are you sure you have connected your form to the application?");
            return;
        }
        patientRepository.save(patient);
    }

    @PostConstruct
    public void populateTestData() {
        if (hospitalRepository.count() == 0) {
            hospitalRepository.saveAll(
                    Stream.of("The Royal Free Hospital", "St Mary’s Hospital", "Moorfields Private eye Hospital",
                            "Charing Cross Hospital", "Hammersmith Hospital", "Chelsea and Westminster Hospital")
                            .map(Hospital::new)
                            .collect(Collectors.toList()));
        }

       if (patientRepository.count() == 0) {
            Random r = new Random(0);
            List<Hospital> hospitals = hospitalRepository.findAll();
            patientRepository.saveAll(
                    Stream.of("Gabrielle Patel", "Brian Robinson", "Eduardo Haugen",
                            "Koen Johansen", "Alejandro Macdonald", "Angel Karlsson", "Yahir Gustavsson", "Haiden Svensson",
                            "Emily Stewart", "Corinne Davis", "Ryann Davis", "Yurem Jackson", "Kelly Gustavsson",
                            "Eileen Walker", "Katelyn Martin", "Israel Carlsson", "Quinn Hansson", "Makena Smith",
                            "Danielle Watson", "Leland Harris", "Gunner Karlsen", "Jamar Olsson", "Lara Martin",
                            "Ann Andersson", "Remington Andersson", "Rene Carlsson", "Elvis Olsen", "Solomon Olsen",
                            "Jaydan Jackson", "Bernard Nilsen")
                            .map(name -> {
                                String[] split = name.split(" ");
                                Patient patient = new Patient();
                                patient.setFirstName(split[0]);
                                patient.setLastName(split[1]);
                                patient.setHospital(hospitals.get(r.nextInt(hospitals.size())));
                                patient.setCondition(Patient.Condition.values()[r.nextInt(Patient.Condition.values().length)]);
                                String email = (patient.getFirstName() + "." + patient.getLastName() + "@gmail.com").toLowerCase();
                                patient.setEmail(email);
                                return patient;
                            }).collect(Collectors.toList()));
        }
    }
}
