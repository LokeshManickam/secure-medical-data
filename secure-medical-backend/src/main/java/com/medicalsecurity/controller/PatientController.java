package com.medicalsecurity.controller;

import com.medicalsecurity.entity.Patient;
import com.medicalsecurity.entity.User;
import com.medicalsecurity.repository.UserRepository;
import com.medicalsecurity.service.PatientAuthorizationService;
import com.medicalsecurity.service.PatientService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/patients")
public class PatientController {

    private final PatientService patientService;
    private final PatientAuthorizationService patientAuthorizationService;
    private final UserRepository userRepository;

    public PatientController(
            PatientService patientService,
            PatientAuthorizationService patientAuthorizationService,
            UserRepository userRepository) {

        this.patientService = patientService;
        this.patientAuthorizationService = patientAuthorizationService;
        this.userRepository = userRepository;
    }

    @PostMapping
    public ResponseEntity<Patient> createPatient(
            @RequestBody Patient patient) {

        Patient savedPatient =
                patientService.createPatient(patient);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(savedPatient);
    }

    @GetMapping
    public ResponseEntity<List<Patient>> getAllPatients() {

        return ResponseEntity.ok(
                patientService.getAllPatients()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<Patient> getPatientById(
            @PathVariable Long id,
            Authentication authentication) {

        User user = getAuthenticatedUser(authentication);

        if (!patientAuthorizationService.canAccessPatient(id, user)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        return ResponseEntity.ok(
                patientService.getPatientById(id)
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<Patient> updatePatient(
            @PathVariable Long id,
            @RequestBody Patient patient,
            Authentication authentication) {

        User user = getAuthenticatedUser(authentication);

        if (!patientAuthorizationService.canAccessPatient(id, user)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        return ResponseEntity.ok(
                patientService.updatePatient(id, patient)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePatient(
            @PathVariable Long id,
            Authentication authentication) {

        User user = getAuthenticatedUser(authentication);

        if (!patientAuthorizationService.canAccessPatient(id, user)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        patientService.deletePatient(id);

        return ResponseEntity.noContent().build();
    }

    private User getAuthenticatedUser(
            Authentication authentication) {

        return userRepository
                .findByUsername(authentication.getName())
                .orElseThrow(() ->
                        new IllegalStateException(
                                "Authenticated user not found"
                        )
                );
    }
}