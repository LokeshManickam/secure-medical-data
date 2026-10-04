package com.medicalsecurity.repository;

import com.medicalsecurity.entity.PatientAccessGrant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PatientAccessGrantRepository
        extends JpaRepository<PatientAccessGrant, Long> {

    Optional<PatientAccessGrant>
    findByPatient_IdAndUser_IdAndActiveTrue(
            Long patientId,
            Long userId
    );

    List<PatientAccessGrant>
    findByPatient_IdAndActiveTrue(
            Long patientId
    );

    List<PatientAccessGrant>
    findByUser_IdAndActiveTrue(
            Long userId
    );
}
