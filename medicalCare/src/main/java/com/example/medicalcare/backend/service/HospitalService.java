package com.example.medicalcare.backend.service;

import com.example.medicalcare.backend.entity.Hospital;
import com.example.medicalcare.backend.repository.HospitalRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HospitalService {
        private HospitalRepository hospitalRepository;

        public HospitalService(HospitalRepository hospitalRepository){
            this.hospitalRepository = hospitalRepository;
        }

        public List<Hospital> findAll() {
            return hospitalRepository.findAll();
        }
}
