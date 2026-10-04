package com.medicalsecurity.service;

import com.medicalsecurity.entity.Role;
import com.medicalsecurity.entity.User;
import org.springframework.stereotype.Service;

@Service
public class PatientAuthorizationService {

    private final PatientAccessGrantService patientAccessGrantService;

    public PatientAuthorizationService(
            PatientAccessGrantService patientAccessGrantService) {

        this.patientAccessGrantService =
                patientAccessGrantService;
    }

    /**
     * Checks whether a user is allowed to access a patient.
     *
     * ADMIN and HEAD_DOCTOR have administrative access.
     *
     * DOCTOR and NURSE require an active patient access grant.
     */
    public boolean canAccessPatient(
            Long patientId,
            User user) {

        if (patientId == null || user == null) {
            return false;
        }

        Role role = user.getRole();

        // Administrative roles can access patient data.
        if (role == Role.ADMIN ||
                role == Role.HEAD_DOCTOR) {

            return true;
        }

        // Clinical users require an active grant.
        if (role == Role.DOCTOR ||
                role == Role.NURSE) {

            return patientAccessGrantService.hasActiveAccess(
                    patientId,
                    user.getId()
            );
        }

        // Other roles do not receive direct patient access.
        return false;
    }
}