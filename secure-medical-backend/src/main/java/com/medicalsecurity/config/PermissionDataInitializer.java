package com.medicalsecurity.config;

import com.medicalsecurity.entity.Permission;
import com.medicalsecurity.repositories.PermissionRepository;
import com.medicalsecurity.security.PermissionNames;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PermissionDataInitializer {

    @Bean
    CommandLineRunner initializePermissions(
            PermissionRepository permissionRepository) {

        return args -> {

            createIfMissing(
                    permissionRepository,
                    PermissionNames.PATIENT_VIEW,
                    "View patient information"
            );

            createIfMissing(
                    permissionRepository,
                    PermissionNames.PATIENT_CREATE,
                    "Create patient records"
            );

            createIfMissing(
                    permissionRepository,
                    PermissionNames.PATIENT_UPDATE,
                    "Update patient information"
            );

            createIfMissing(
                    permissionRepository,
                    PermissionNames.PATIENT_DELETE,
                    "Delete patient records"
            );

            createIfMissing(
                    permissionRepository,
                    PermissionNames.MEDICAL_RECORD_VIEW,
                    "View medical records"
            );

            createIfMissing(
                    permissionRepository,
                    PermissionNames.MEDICAL_RECORD_CREATE,
                    "Create medical records"
            );

            createIfMissing(
                    permissionRepository,
                    PermissionNames.MEDICAL_RECORD_UPDATE,
                    "Update medical records"
            );

            createIfMissing(
                    permissionRepository,
                    PermissionNames.PRESCRIPTION_VIEW,
                    "View prescriptions"
            );

            createIfMissing(
                    permissionRepository,
                    PermissionNames.PRESCRIPTION_CREATE,
                    "Create prescriptions"
            );

            createIfMissing(
                    permissionRepository,
                    PermissionNames.PRESCRIPTION_UPDATE,
                    "Update prescriptions"
            );

            createIfMissing(
                    permissionRepository,
                    PermissionNames.LAB_ORDER_VIEW,
                    "View laboratory orders"
            );

            createIfMissing(
                    permissionRepository,
                    PermissionNames.LAB_ORDER_CREATE,
                    "Create laboratory orders"
            );

            createIfMissing(
                    permissionRepository,
                    PermissionNames.LAB_REPORT_VIEW,
                    "View laboratory reports"
            );

            createIfMissing(
                    permissionRepository,
                    PermissionNames.LAB_REPORT_CREATE,
                    "Create laboratory reports"
            );

            createIfMissing(
                    permissionRepository,
                    PermissionNames.PATIENT_ACCESS_REQUEST,
                    "Request access to a patient"
            );

            createIfMissing(
                    permissionRepository,
                    PermissionNames.PATIENT_ACCESS_APPROVE,
                    "Approve patient access requests"
            );

            createIfMissing(
                    permissionRepository,
                    PermissionNames.PATIENT_ACCESS_REVOKE,
                    "Revoke patient access"
            );

            createIfMissing(
                    permissionRepository,
                    PermissionNames.SECURE_MESSAGE_SEND,
                    "Send secure messages"
            );

            createIfMissing(
                    permissionRepository,
                    PermissionNames.SECURE_MESSAGE_READ,
                    "Read secure messages"
            );

            createIfMissing(
                    permissionRepository,
                    PermissionNames.USER_VIEW,
                    "View user information"
            );

            createIfMissing(
                    permissionRepository,
                    PermissionNames.USER_CREATE,
                    "Create users"
            );

            createIfMissing(
                    permissionRepository,
                    PermissionNames.USER_UPDATE,
                    "Update user information"
            );

            createIfMissing(
                    permissionRepository,
                    PermissionNames.USER_DISABLE,
                    "Disable user accounts"
            );

            createIfMissing(
                    permissionRepository,
                    PermissionNames.AUDIT_LOG_VIEW,
                    "View audit logs"
            );

            createIfMissing(
                    permissionRepository,
                    PermissionNames.SECURITY_EVENT_VIEW,
                    "View security events"
            );

            createIfMissing(
                    permissionRepository,
                    PermissionNames.SECURITY_EVENT_MANAGE,
                    "Manage security events");
        };
    }

    private void createIfMissing(
            PermissionRepository permissionRepository,
            String name,
            String description) {

        if (!permissionRepository.existsByName(name)) {
            permissionRepository.save(
                    new Permission(name, description)
            );
        }
    }
}