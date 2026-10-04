package com.medicalsecurity.service;

import com.medicalsecurity.entity.Patient;
import com.medicalsecurity.entity.PatientAccessGrant;
import com.medicalsecurity.entity.Role;
import com.medicalsecurity.entity.User;
import com.medicalsecurity.repository.PatientAccessGrantRepository;
import com.medicalsecurity.repository.PatientRepository;
import com.medicalsecurity.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PatientAccessGrantService {

    private final PatientAccessGrantRepository patientAccessGrantRepository;
    private final PatientRepository patientRepository;
    private final UserRepository userRepository;

    public PatientAccessGrantService(
            PatientAccessGrantRepository patientAccessGrantRepository,
            PatientRepository patientRepository,
            UserRepository userRepository) {

        this.patientAccessGrantRepository =
                patientAccessGrantRepository;

        this.patientRepository =
                patientRepository;

        this.userRepository =
                userRepository;
    }

    public PatientAccessGrant createGrant(
            Long patientId,
            Long userId,
            String approvedByUsername,
            LocalDateTime expiresAt) {

        // 1. Find patient.
        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Patient not found"
                        ));

        // 2. Find the user receiving access.
        User user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "User not found"
                        ));

        // 3. Find the authenticated approver.
        User approvedBy = userRepository
                .findByUsername(approvedByUsername)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Approving user not found"
                        ));

        // 4. Validate the person receiving access.
        validateTargetUser(user);

        // 5. Validate the person approving access.
        validateApprover(approvedBy);

        // 6. Validate expiry.
        validateExpiry(expiresAt);

        // 7. Prevent duplicate active access.
        boolean alreadyGranted =
                patientAccessGrantRepository
                        .findByPatient_IdAndUser_IdAndActiveTrue(
                                patientId,
                                userId
                        )
                        .isPresent();

        if (alreadyGranted) {
            throw new IllegalArgumentException(
                    "Active access grant already exists"
            );
        }

        // 8. Create the access grant.
        PatientAccessGrant grant =
                new PatientAccessGrant(
                        patient,
                        user,
                        approvedBy,
                        expiresAt
                );

        return patientAccessGrantRepository.save(grant);
    }

    public List<PatientAccessGrant> getPatientGrants(
            Long patientId) {

        patientRepository.findById(patientId)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Patient not found"
                        ));

        return patientAccessGrantRepository
                .findByPatient_IdAndActiveTrue(patientId);
    }

    public List<PatientAccessGrant> getUserGrants(
            Long userId) {

        userRepository.findById(userId)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "User not found"
                        ));

        return patientAccessGrantRepository
                .findByUser_IdAndActiveTrue(userId);
    }

    public void revokeGrant(Long grantId) {

        PatientAccessGrant grant =
                patientAccessGrantRepository.findById(grantId)
                        .orElseThrow(() ->
                                new IllegalArgumentException(
                                        "Access grant not found"
                                ));

        grant.setActive(false);

        patientAccessGrantRepository.save(grant);
    }

    public boolean hasActiveAccess(
            Long patientId,
            Long userId) {

        PatientAccessGrant grant =
                patientAccessGrantRepository
                        .findByPatient_IdAndUser_IdAndActiveTrue(
                                patientId,
                                userId
                        )
                        .orElse(null);

        if (grant == null) {
            return false;
        }

        // No expiry means the grant remains active
        // until explicitly revoked.
        if (grant.getExpiresAt() == null) {
            return true;
        }

        // Automatically treat expired grants as invalid.
        return LocalDateTime.now()
                .isBefore(grant.getExpiresAt());
    }

    private void validateTargetUser(User user) {

        Role role = user.getRole();

        if (role != Role.DOCTOR
                && role != Role.NURSE) {

            throw new IllegalArgumentException(
                    "Only clinical users can receive patient access grants"
            );
        }
    }

    private void validateApprover(User user) {

        Role role = user.getRole();

        if (role != Role.HEAD_DOCTOR) {

            throw new IllegalArgumentException(
                    "Only a HEAD_DOCTOR can approve patient access grants"
            );
        }
    }

    private void validateExpiry(LocalDateTime expiresAt) {

        if (expiresAt != null &&
                !expiresAt.isAfter(LocalDateTime.now())) {

            throw new IllegalArgumentException(
                    "Access expiry must be in the future"
            );
        }
    }
}