package com.medicalsecurity.controller;

import com.medicalsecurity.entity.PatientAccessGrant;
import com.medicalsecurity.entity.PatientAccessRequest;
import com.medicalsecurity.service.PatientAccessRequestService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/patient-access-requests")
public class PatientAccessRequestController {

    private final PatientAccessRequestService
            patientAccessRequestService;

    public PatientAccessRequestController(
            PatientAccessRequestService patientAccessRequestService) {

        this.patientAccessRequestService =
                patientAccessRequestService;
    }

    /*
     * Create a new patient access request.
     *
     * Example:
     * POST /api/patient-access-requests
     */
    @PostMapping
    public ResponseEntity<PatientAccessRequest> createRequest(
            @RequestParam Long patientId,
            @RequestParam Long requesterId,
            @RequestParam(required = false) String reason) {

        PatientAccessRequest request =
                patientAccessRequestService.createRequest(
                        patientId,
                        requesterId,
                        reason
                );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(request);
    }

    /*
     * Get all pending access requests.
     *
     * ADMIN and HEAD_DOCTOR will use this endpoint
     * to see requests waiting for approval.
     */
    @GetMapping("/pending")
    public ResponseEntity<List<PatientAccessRequest>>
    getPendingRequests() {

        return ResponseEntity.ok(
                patientAccessRequestService
                        .getPendingRequests()
        );
    }

    /*
     * Approve an access request.
     *
     * Approval also creates a PatientAccessGrant.
     */
    @PostMapping("/{requestId}/approve")
    public ResponseEntity<PatientAccessGrant> approveRequest(
            @PathVariable Long requestId,
            @RequestParam Long reviewerId) {

        PatientAccessGrant grant =
                patientAccessRequestService
                        .approveRequest(
                                requestId,
                                reviewerId
                        );

        return ResponseEntity.ok(grant);
    }

    /*
     * Reject an access request.
     */
    @PostMapping("/{requestId}/reject")
    public ResponseEntity<PatientAccessRequest> rejectRequest(
            @PathVariable Long requestId,
            @RequestParam Long reviewerId) {

        PatientAccessRequest request =
                patientAccessRequestService
                        .rejectRequest(
                                requestId,
                                reviewerId
                        );

        return ResponseEntity.ok(request);
    }
}