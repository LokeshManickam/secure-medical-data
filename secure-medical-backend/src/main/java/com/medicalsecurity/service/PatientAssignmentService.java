package com.medicalsecurity.service;

import com.medicalsecurity.entity.Patient;
import com.medicalsecurity.entity.PatientAssignment;
import com.medicalsecurity.entity.Role;
import com.medicalsecurity.entity.User;
import com.medicalsecurity.repository.PatientAssignmentRepository;
import com.medicalsecurity.repository.PatientRepository;
import com.medicalsecurity.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PatientAssignmentService {

    private final PatientAssignmentRepository patientAssignmentRepository;
    private final PatientRepository patientRepository;
    private final UserRepository userRepository;

    public PatientAssignmentService(
            PatientAssignmentRepository patientAssignmentRepository,
            PatientRepository patientRepository,
            UserRepository userRepository) {

        this.patientAssignmentRepository = patientAssignmentRepository;
        this.patientRepository = patientRepository;
        this.userRepository = userRepository;
    }

    /*
     * Assign a healthcare user to a patient.
     *
     * assignedByUsername comes from the authenticated
     * Spring Security user rather than from the client.
     */
    public PatientAssignment assignPatient(
            Long patientId,
            Long userId,
            String assignedByUsername) {

        // Find the patient.
        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Patient not found"
                        ));

        // Find the user receiving the assignment.
        User user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "User not found"
                        ));

        // Find the authenticated user who is creating
        // the assignment.
        User assignedBy = userRepository
                .findByUsername(assignedByUsername)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Authenticated user not found"
                        ));

        // Check whether the target user can receive
        // a patient assignment.
        validateAssignableRole(user);

        // Prevent duplicate active assignments.
        boolean alreadyAssigned =
                patientAssignmentRepository
                        .existsByPatient_IdAndUser_IdAndActiveTrue(
                                patientId,
                                userId
                        );

        if (alreadyAssigned) {
            throw new IllegalArgumentException(
                    "User is already actively assigned to this patient"
            );
        }

        // Create the assignment.
        PatientAssignment assignment =
                new PatientAssignment(
                        patient,
                        user,
                        assignedBy
                );

        // Save the assignment.
        return patientAssignmentRepository.save(assignment);
    }

    /*
     * Return all active assignments for a patient.
     */
    public List<PatientAssignment> getAssignmentsForPatient(
            Long patientId) {

        patientRepository.findById(patientId)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Patient not found"
                        ));

        return patientAssignmentRepository
                .findByPatient_IdAndActiveTrue(patientId);
    }

    /*
     * Return all active patient assignments for a user.
     */
    public List<PatientAssignment> getAssignmentsForUser(
            Long userId) {

        userRepository.findById(userId)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "User not found"
                        ));

        return patientAssignmentRepository
                .findByUser_IdAndActiveTrue(userId);
    }

    /*
     * Deactivate an existing assignment.
     *
     * We keep the database record for historical purposes
     * instead of physically deleting it.
     */
    public void deactivateAssignment(Long assignmentId) {

        PatientAssignment assignment =
                patientAssignmentRepository.findById(assignmentId)
                        .orElseThrow(() ->
                                new IllegalArgumentException(
                                        "Patient assignment not found"
                                ));

        assignment.setActive(false);

        patientAssignmentRepository.save(assignment);
    }

    /*
     * Only selected healthcare roles can receive
     * a patient assignment.
     */
    private void validateAssignableRole(User user) {

        Role role = user.getRole();

        if (role != Role.HEAD_DOCTOR
                && role != Role.DOCTOR
                && role != Role.NURSE) {

            throw new IllegalArgumentException(
                    "This user role cannot be assigned to a patient"
            );
        }
    }
}