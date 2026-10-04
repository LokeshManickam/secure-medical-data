package com.medicalsecurity.controller;

import com.medicalsecurity.entity.PatientAccessGrant;
import com.medicalsecurity.service.PatientAccessGrantService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/patient-access-grants")
public class PatientAccessGrantController {

    private final PatientAccessGrantService
            patientAccessGrantService;

    public PatientAccessGrantController(
            PatientAccessGrantService patientAccessGrantService) {

        this.patientAccessGrantService =
                patientAccessGrantService;
    }

    @PreAuthorize("hasAuthority('PATIENT_ACCESS_APPROVE')")
    @PostMapping
    public ResponseEntity<PatientAccessGrant> createGrant(
            @RequestParam Long patientId,
            @RequestParam Long userId,
            @RequestParam(required = false)
            LocalDateTime expiresAt,
            Authentication authentication) {

        PatientAccessGrant grant =
                patientAccessGrantService.createGrant(
                        patientId,
                        userId,
                        authentication.getName(),
                        expiresAt
                );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(grant);
    }

    @PreAuthorize("hasAuthority('PATIENT_ACCESS_APPROVE')")
    @GetMapping("/patient/{patientId}")
    public ResponseEntity<List<PatientAccessGrant>>
    getPatientGrants(
            @PathVariable Long patientId) {

        return ResponseEntity.ok(
                patientAccessGrantService
                        .getPatientGrants(patientId)
        );
    }

    @PreAuthorize("hasAuthority('PATIENT_ACCESS_APPROVE')")
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<PatientAccessGrant>>
    getUserGrants(
            @PathVariable Long userId) {

        return ResponseEntity.ok(
                patientAccessGrantService
                        .getUserGrants(userId)
        );
    }

    @PreAuthorize("hasAuthority('PATIENT_ACCESS_REVOKE')")
    @DeleteMapping("/{grantId}")
    public ResponseEntity<Void> revokeGrant(
            @PathVariable Long grantId) {

        patientAccessGrantService.revokeGrant(grantId);

        return ResponseEntity.noContent().build();
    }
}