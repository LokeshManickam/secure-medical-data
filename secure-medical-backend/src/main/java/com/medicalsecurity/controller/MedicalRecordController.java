package com.medicalsecurity.controller;

import com.medicalsecurity.entity.MedicalRecord;
import com.medicalsecurity.entity.User;
import com.medicalsecurity.repository.UserRepository;
import com.medicalsecurity.service.MedicalRecordService;
import com.medicalsecurity.service.PatientAuthorizationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class MedicalRecordController {

    private final MedicalRecordService medicalRecordService;
    private final PatientAuthorizationService
            patientAuthorizationService;
    private final UserRepository userRepository;

    public MedicalRecordController(
            MedicalRecordService medicalRecordService,
            PatientAuthorizationService patientAuthorizationService,
            UserRepository userRepository) {

        this.medicalRecordService =
                medicalRecordService;

        this.patientAuthorizationService =
                patientAuthorizationService;

        this.userRepository =
                userRepository;
    }

    /**
     * Create a medical record for a patient.
     */
    @PostMapping("/patients/{patientId}/medical-records")
    @PreAuthorize(
            "hasAuthority('MEDICAL_RECORD_CREATE')"
    )
    public ResponseEntity<MedicalRecord> createMedicalRecord(
            @PathVariable Long patientId,
            @RequestBody MedicalRecord medicalRecord,
            Authentication authentication) {

        User user =
                getAuthenticatedUser(authentication);

        if (!patientAuthorizationService.canAccessPatient(
                patientId,
                user)) {

            return ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .build();
        }

        MedicalRecord savedRecord =
                medicalRecordService.createMedicalRecord(
                        patientId,
                        medicalRecord
                );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(savedRecord);
    }

    /**
     * Get all medical records belonging to a patient.
     */
    @GetMapping("/patients/{patientId}/medical-records")
    @PreAuthorize(
            "hasAuthority('MEDICAL_RECORD_VIEW')"
    )
    public ResponseEntity<List<MedicalRecord>>
    getPatientMedicalRecords(
            @PathVariable Long patientId,
            Authentication authentication) {

        System.out.println(
                ">>> GET MEDICAL RECORDS CONTROLLER ENTERED"
        );

        System.out.println(
                ">>> AUTHENTICATED USER = "
                        + authentication.getName()
        );

        User user =
                getAuthenticatedUser(authentication);

        System.out.println(
                ">>> USER ROLE = "
                        + user.getRole()
        );

        System.out.println(
                ">>> CAN ACCESS PATIENT = "
                        + patientAuthorizationService.canAccessPatient(
                        patientId,
                        user
                )
        );

        if (!patientAuthorizationService.canAccessPatient(
                patientId,
                user)) {

            return ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .build();
        }

        return ResponseEntity.ok(
                medicalRecordService
                        .getPatientMedicalRecords(patientId)
        );
    }

    /**
     * Get one medical record.
     */
    @GetMapping("/medical-records/{id}")
    @PreAuthorize(
            "hasAuthority('MEDICAL_RECORD_VIEW')"
    )
    public ResponseEntity<MedicalRecord>
    getMedicalRecordById(
            @PathVariable Long id,
            Authentication authentication) {

        MedicalRecord record =
                medicalRecordService
                        .getMedicalRecordById(id);

        User user =
                getAuthenticatedUser(authentication);

        Long patientId =
                record.getPatient().getId();

        if (!patientAuthorizationService.canAccessPatient(
                patientId,
                user)) {

            return ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .build();
        }

        return ResponseEntity.ok(record);
    }

    /**
     * Update a medical record.
     */
    @PutMapping("/medical-records/{id}")
    @PreAuthorize(
            "hasAuthority('MEDICAL_RECORD_UPDATE')"
    )
    public ResponseEntity<MedicalRecord>
    updateMedicalRecord(
            @PathVariable Long id,
            @RequestBody MedicalRecord updatedRecord,
            Authentication authentication) {

        MedicalRecord existingRecord =
                medicalRecordService
                        .getMedicalRecordById(id);

        User user =
                getAuthenticatedUser(authentication);

        Long patientId =
                existingRecord.getPatient().getId();

        if (!patientAuthorizationService.canAccessPatient(
                patientId,
                user)) {

            return ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .build();
        }

        MedicalRecord savedRecord =
                medicalRecordService.updateMedicalRecord(
                        id,
                        updatedRecord
                );

        return ResponseEntity.ok(savedRecord);
    }

    private User getAuthenticatedUser(
            Authentication authentication) {

        return userRepository
                .findByUsername(authentication.getName())
                .orElseThrow(() ->
                        new IllegalStateException(
                                "Authenticated user not found"
                        ));
    }
}