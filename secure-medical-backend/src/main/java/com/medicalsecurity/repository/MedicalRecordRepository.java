package com.medicalsecurity.repository;

import com.medicalsecurity.entity.MedicalRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MedicalRecordRepository
        extends JpaRepository<MedicalRecord, Long> {

    List<MedicalRecord> findByPatient_IdOrderByRecordDateDesc(
            Long patientId
    );
}