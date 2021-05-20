package com.example.medicalcare.backend.repository;

import com.example.medicalcare.backend.entity.Hospital;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HospitalRepository extends JpaRepository<Hospital, Long> {
}
