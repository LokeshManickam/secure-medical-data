package com.medicalsecurity.repository;

import com.medicalsecurity.entity.PatientAssignment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PatientAssignmentRepository
        extends JpaRepository<PatientAssignment, Long> {

    /*
     * Check whether a user is actively assigned to a patient.
     */
    boolean existsByPatient_IdAndUser_IdAndActiveTrue(
            Long patientId,
            Long userId
    );

    /*
     * Find an active assignment between a patient and user.
     */
    Optional<PatientAssignment>
    findByPatient_IdAndUser_IdAndActiveTrue(
            Long patientId,
            Long userId
    );

    /*
     * Find all active assignments for a particular patient.
     */
    List<PatientAssignment>
    findByPatient_IdAndActiveTrue(
            Long patientId
    );

    /*
     * Find all active patient assignments for a particular user.
     */
    List<PatientAssignment>
    findByUser_IdAndActiveTrue(
            Long userId
    );
}