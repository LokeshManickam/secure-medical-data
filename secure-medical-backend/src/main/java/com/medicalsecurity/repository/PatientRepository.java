package com.medicalsecurity.repository;

import com.medicalsecurity.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientRepository extends JpaRepository<Patient, Long> {

    boolean existsByPatientCode(String patientCode);
}