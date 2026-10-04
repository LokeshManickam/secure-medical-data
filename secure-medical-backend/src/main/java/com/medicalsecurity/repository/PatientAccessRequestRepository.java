package com.medicalsecurity.repository;

import com.medicalsecurity.entity.AccessRequestStatus;
import com.medicalsecurity.entity.PatientAccessRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PatientAccessRequestRepository
        extends JpaRepository<PatientAccessRequest, Long> {

    /*
     * Find all access requests having a particular status.
     *
     * Example:
     * findByStatus(PENDING)
     *
     * returns all pending access requests.
     */
    List<PatientAccessRequest> findByStatus(
            AccessRequestStatus status
    );

    /*
     * Check whether the same user already has a request
     * for the same patient with the specified status.
     *
     * We will mainly use this to prevent duplicate
     * PENDING requests.
     */
    boolean existsByPatient_IdAndRequester_IdAndStatus(
            Long patientId,
            Long requesterId,
            AccessRequestStatus status
    );
}