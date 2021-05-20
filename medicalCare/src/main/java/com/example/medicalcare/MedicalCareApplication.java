package com.example.medicalcare;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication(scanBasePackages = "com.example.medicalcare")
public class MedicalCareApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(MedicalCareApplication.class, args);
    }

}
