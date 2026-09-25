package com.medicalsecurity.config;

import com.medicalsecurity.entity.Permission;
import com.medicalsecurity.entity.Role;
import com.medicalsecurity.entity.RolePermission;
import com.medicalsecurity.repositories.PermissionRepository;
import com.medicalsecurity.repository.RolePermissionRepository;
import com.medicalsecurity.security.PermissionNames;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class RolePermissionDataInitializer {

    @Bean
    CommandLineRunner initializeRolePermissions(
            PermissionRepository permissionRepository,
            RolePermissionRepository rolePermissionRepository) {

        return args -> {

            // ADMIN
            assignPermissions(
                    Role.ADMIN,
                    List.of(
                            PermissionNames.PATIENT_VIEW,
                            PermissionNames.PATIENT_CREATE,
                            PermissionNames.PATIENT_UPDATE,
                            PermissionNames.PATIENT_DELETE,
                            PermissionNames.MEDICAL_RECORD_VIEW,
                            PermissionNames.MEDICAL_RECORD_CREATE,
                            PermissionNames.MEDICAL_RECORD_UPDATE,
                            PermissionNames.PRESCRIPTION_VIEW,
                            PermissionNames.PRESCRIPTION_CREATE,
                            PermissionNames.PRESCRIPTION_UPDATE,
                            PermissionNames.LAB_ORDER_VIEW,
                            PermissionNames.LAB_ORDER_CREATE,
                            PermissionNames.LAB_REPORT_VIEW,
                            PermissionNames.LAB_REPORT_CREATE,
                            PermissionNames.PATIENT_ACCESS_REQUEST,
                            PermissionNames.PATIENT_ACCESS_APPROVE,
                            PermissionNames.PATIENT_ACCESS_REVOKE,
                            PermissionNames.SECURE_MESSAGE_SEND,
                            PermissionNames.SECURE_MESSAGE_READ,
                            PermissionNames.USER_VIEW,
                            PermissionNames.USER_CREATE,
                            PermissionNames.USER_UPDATE,
                            PermissionNames.USER_DISABLE,
                            PermissionNames.AUDIT_LOG_VIEW,
                            PermissionNames.SECURITY_EVENT_VIEW,
                            PermissionNames.SECURITY_EVENT_MANAGE
                    ),
                    permissionRepository,
                    rolePermissionRepository
            );

            // HEAD DOCTOR
            assignPermissions(
                    Role.HEAD_DOCTOR,
                    List.of(
                            PermissionNames.PATIENT_VIEW,
                            PermissionNames.PATIENT_CREATE,
                            PermissionNames.PATIENT_UPDATE,
                            PermissionNames.MEDICAL_RECORD_VIEW,
                            PermissionNames.MEDICAL_RECORD_CREATE,
                            PermissionNames.MEDICAL_RECORD_UPDATE,
                            PermissionNames.PRESCRIPTION_VIEW,
                            PermissionNames.PRESCRIPTION_CREATE,
                            PermissionNames.PRESCRIPTION_UPDATE,
                            PermissionNames.LAB_ORDER_VIEW,
                            PermissionNames.LAB_ORDER_CREATE,
                            PermissionNames.LAB_REPORT_VIEW,
                            PermissionNames.LAB_REPORT_CREATE,
                            PermissionNames.PATIENT_ACCESS_REQUEST,
                            PermissionNames.PATIENT_ACCESS_APPROVE,
                            PermissionNames.PATIENT_ACCESS_REVOKE,
                            PermissionNames.SECURE_MESSAGE_SEND,
                            PermissionNames.SECURE_MESSAGE_READ,
                            PermissionNames.USER_VIEW,
                            PermissionNames.AUDIT_LOG_VIEW,
                            PermissionNames.SECURITY_EVENT_VIEW
                    ),
                    permissionRepository,
                    rolePermissionRepository
            );

            // DOCTOR
            assignPermissions(
                    Role.DOCTOR,
                    List.of(
                            PermissionNames.PATIENT_VIEW,
                            PermissionNames.PATIENT_UPDATE,
                            PermissionNames.MEDICAL_RECORD_VIEW,
                            PermissionNames.MEDICAL_RECORD_CREATE,
                            PermissionNames.MEDICAL_RECORD_UPDATE,
                            PermissionNames.PRESCRIPTION_VIEW,
                            PermissionNames.PRESCRIPTION_CREATE,
                            PermissionNames.PRESCRIPTION_UPDATE,
                            PermissionNames.LAB_ORDER_VIEW,
                            PermissionNames.LAB_REPORT_VIEW,
                            PermissionNames.PATIENT_ACCESS_REQUEST,
                            PermissionNames.SECURE_MESSAGE_SEND,
                            PermissionNames.SECURE_MESSAGE_READ
                    ),
                    permissionRepository,
                    rolePermissionRepository
            );

            // NURSE
            assignPermissions(
                    Role.NURSE,
                    List.of(
                            PermissionNames.PATIENT_VIEW,
                            PermissionNames.MEDICAL_RECORD_VIEW,
                            PermissionNames.MEDICAL_RECORD_CREATE,
                            PermissionNames.LAB_REPORT_VIEW,
                            PermissionNames.SECURE_MESSAGE_SEND,
                            PermissionNames.SECURE_MESSAGE_READ
                    ),
                    permissionRepository,
                    rolePermissionRepository
            );

            // RECEPTIONIST
            assignPermissions(
                    Role.RECEPTIONIST,
                    List.of(
                            PermissionNames.PATIENT_VIEW,
                            PermissionNames.PATIENT_CREATE,
                            PermissionNames.PATIENT_UPDATE
                    ),
                    permissionRepository,
                    rolePermissionRepository
            );

            // LAB TECHNICIAN
            assignPermissions(
                    Role.LAB_TECHNICIAN,
                    List.of(
                            PermissionNames.PATIENT_VIEW,
                            PermissionNames.LAB_ORDER_VIEW,
                            PermissionNames.LAB_REPORT_VIEW,
                            PermissionNames.LAB_REPORT_CREATE
                    ),
                    permissionRepository,
                    rolePermissionRepository
            );

            // PHARMACIST
            assignPermissions(
                    Role.PHARMACIST,
                    List.of(
                            PermissionNames.PATIENT_VIEW,
                            PermissionNames.PRESCRIPTION_VIEW,
                            PermissionNames.PRESCRIPTION_UPDATE
                    ),
                    permissionRepository,
                    rolePermissionRepository
            );

            // SECURITY AUDITOR
            assignPermissions(
                    Role.SECURITY_AUDITOR,
                    List.of(
                            PermissionNames.AUDIT_LOG_VIEW,
                            PermissionNames.SECURITY_EVENT_VIEW,
                            PermissionNames.SECURITY_EVENT_MANAGE
                    ),
                    permissionRepository,
                    rolePermissionRepository
            );
        };
    }

    private void assignPermissions(
            Role role,
            List<String> permissionNames,
            PermissionRepository permissionRepository,
            RolePermissionRepository rolePermissionRepository) {

        for (String permissionName : permissionNames) {

            Permission permission = permissionRepository
                    .findByName(permissionName)
                    .orElseThrow(() ->
                            new IllegalStateException(
                                    "Permission not found: " + permissionName
                            ));

            if (!rolePermissionRepository
                    .existsByRoleAndPermission_Name(role, permissionName)) {

                rolePermissionRepository.save(
                        new RolePermission(role, permission)
                );
            }
        }
    }
}