package com.medicalsecurity.controller;

import com.medicalsecurity.entity.PatientAssignment;
import com.medicalsecurity.service.PatientAssignmentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/patient-assignments")
public class PatientAssignmentController {

    private final PatientAssignmentService patientAssignmentService;

    public PatientAssignmentController(
            PatientAssignmentService patientAssignmentService) {

        this.patientAssignmentService = patientAssignmentService;
    }

    /*
     * Create a patient assignment.
     *
     * The authenticated user's identity comes from
     * Spring Security rather than from the request.
     */
    @PreAuthorize("hasAuthority('PATIENT_ASSIGN')")
    @PostMapping
    public ResponseEntity<PatientAssignment> assignPatient(
            @RequestParam Long patientId,
            @RequestParam Long userId,
            Authentication authentication) {

        PatientAssignment assignment =
                patientAssignmentService.assignPatient(
                        patientId,
                        userId,
                        authentication.getName()
                );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(assignment);
    }

    /*
     * Get all active assignments for a patient.
     */
    @PreAuthorize("hasAuthority('PATIENT_ASSIGN')")
    @GetMapping("/patient/{patientId}")
    public ResponseEntity<List<PatientAssignment>>
    getAssignmentsForPatient(
            @PathVariable Long patientId) {

        return ResponseEntity.ok(
                patientAssignmentService
                        .getAssignmentsForPatient(patientId)
        );
    }

    /*
     * Get all active assignments for a user.
     */
    @PreAuthorize("hasAuthority('PATIENT_ASSIGN')")
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<PatientAssignment>>
    getAssignmentsForUser(
            @PathVariable Long userId) {

        return ResponseEntity.ok(
                patientAssignmentService
                        .getAssignmentsForUser(userId)
        );
    }

    /*
     * Deactivate a patient assignment.
     */
    @PreAuthorize("hasAuthority('PATIENT_ASSIGN')")
    @DeleteMapping("/{assignmentId}")
    public ResponseEntity<Void> deactivateAssignment(
            @PathVariable Long assignmentId) {

        patientAssignmentService
                .deactivateAssignment(assignmentId);

        return ResponseEntity.noContent().build();
    }
}