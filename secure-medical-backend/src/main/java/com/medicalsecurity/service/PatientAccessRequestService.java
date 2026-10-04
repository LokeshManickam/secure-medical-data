package com.medicalsecurity.service;

import com.medicalsecurity.entity.AccessRequestStatus;
import com.medicalsecurity.entity.Patient;
import com.medicalsecurity.entity.PatientAccessGrant;
import com.medicalsecurity.entity.PatientAccessRequest;
import com.medicalsecurity.entity.Role;
import com.medicalsecurity.entity.User;
import com.medicalsecurity.repository.PatientAccessGrantRepository;
import com.medicalsecurity.repository.PatientAccessRequestRepository;
import com.medicalsecurity.repository.PatientRepository;
import com.medicalsecurity.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PatientAccessRequestService {

    private final PatientAccessRequestRepository requestRepository;
    private final PatientAccessGrantRepository grantRepository;
    private final PatientRepository patientRepository;
    private final UserRepository userRepository;

    public PatientAccessRequestService(
            PatientAccessRequestRepository requestRepository,
            PatientAccessGrantRepository grantRepository,
            PatientRepository patientRepository,
            UserRepository userRepository) {

        this.requestRepository = requestRepository;
        this.grantRepository = grantRepository;
        this.patientRepository = patientRepository;
        this.userRepository = userRepository;
    }

    /*
     * Create a new patient access request.
     */
    public PatientAccessRequest createRequest(
            Long patientId,
            Long requesterId,
            String reason) {

        Patient patient = patientRepository
                .findById(patientId)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Patient not found"
                        ));

        User requester = userRepository
                .findById(requesterId)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Requester not found"
                        ));

        /*
         * Only clinical users should request direct patient access.
         */
        if (requester.getRole() != Role.DOCTOR &&
                requester.getRole() != Role.NURSE) {

            throw new IllegalArgumentException(
                    "Only doctors and nurses can request patient access"
            );
        }

        /*
         * Prevent duplicate pending requests.
         */
        boolean alreadyPending =
                requestRepository
                        .existsByPatient_IdAndRequester_IdAndStatus(
                                patientId,
                                requesterId,
                                AccessRequestStatus.PENDING
                        );

        if (alreadyPending) {

            throw new IllegalArgumentException(
                    "A pending access request already exists"
            );
        }

        /*
         * Prevent requesting access when the user
         * already has an active grant.
         */
        boolean alreadyHasAccess =
                grantRepository
                        .findByPatient_IdAndUser_IdAndActiveTrue(
                                patientId,
                                requesterId
                        )
                        .isPresent();

        if (alreadyHasAccess) {

            throw new IllegalArgumentException(
                    "User already has active access to this patient"
            );
        }

        PatientAccessRequest request =
                new PatientAccessRequest(
                        patient,
                        requester,
                        reason
                );

        return requestRepository.save(request);
    }

    /*
     * Get all pending access requests.
     */
    public List<PatientAccessRequest> getPendingRequests() {

        return requestRepository.findByStatus(
                AccessRequestStatus.PENDING
        );
    }

    /*
     * Approve an access request.
     *
     * Only ADMIN and HEAD_DOCTOR should call this method.
     */
    public PatientAccessGrant approveRequest(
            Long requestId,
            Long reviewerId) {

        PatientAccessRequest request =
                getRequest(requestId);

        if (request.getStatus() !=
                AccessRequestStatus.PENDING) {

            throw new IllegalArgumentException(
                    "Only pending requests can be approved"
            );
        }

        User reviewer = userRepository
                .findById(reviewerId)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Reviewer not found"
                        ));

        /*
         * Only ADMIN and HEAD_DOCTOR can approve.
         */
        if (reviewer.getRole() != Role.ADMIN &&
                reviewer.getRole() != Role.HEAD_DOCTOR) {

            throw new IllegalArgumentException(
                    "User is not authorized to approve access requests"
            );
        }

        /*
         * Check whether access was already granted.
         */
        boolean alreadyGranted =
                grantRepository
                        .findByPatient_IdAndUser_IdAndActiveTrue(
                                request.getPatient().getId(),
                                request.getRequester().getId()
                        )
                        .isPresent();

        if (alreadyGranted) {

            throw new IllegalArgumentException(
                    "User already has active access to this patient"
            );
        }

        /*
         * Update the request.
         */
        request.setStatus(
                AccessRequestStatus.APPROVED
        );

        request.setReviewedBy(reviewer);

        request.setReviewedAt(
                LocalDateTime.now()
        );

        requestRepository.save(request);

        /*
         * Create the actual access grant.
         */
        PatientAccessGrant grant =
                new PatientAccessGrant(
                        request.getPatient(),
                        request.getRequester(),
                        reviewer,
                        null
                );

        return grantRepository.save(grant);
    }

    /*
     * Reject an access request.
     */
    public PatientAccessRequest rejectRequest(
            Long requestId,
            Long reviewerId) {

        PatientAccessRequest request =
                getRequest(requestId);

        if (request.getStatus() !=
                AccessRequestStatus.PENDING) {

            throw new IllegalArgumentException(
                    "Only pending requests can be rejected"
            );
        }

        User reviewer = userRepository
                .findById(reviewerId)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Reviewer not found"
                        ));

        /*
         * Only ADMIN and HEAD_DOCTOR can reject.
         */
        if (reviewer.getRole() != Role.ADMIN &&
                reviewer.getRole() != Role.HEAD_DOCTOR) {

            throw new IllegalArgumentException(
                    "User is not authorized to reject access requests"
            );
        }

        request.setStatus(
                AccessRequestStatus.REJECTED
        );

        request.setReviewedBy(reviewer);

        request.setReviewedAt(
                LocalDateTime.now()
        );

        return requestRepository.save(request);
    }

    /*
     * Find a request by ID.
     */
    public PatientAccessRequest getRequest(
            Long requestId) {

        return requestRepository
                .findById(requestId)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Access request not found"
                        ));
    }
}