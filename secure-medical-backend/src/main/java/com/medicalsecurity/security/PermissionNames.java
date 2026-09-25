package com.medicalsecurity.security;

public final class PermissionNames {

    private PermissionNames() {
        // Utility class
    }

    // Patient
    public static final String PATIENT_VIEW = "PATIENT_VIEW";
    public static final String PATIENT_CREATE = "PATIENT_CREATE";
    public static final String PATIENT_UPDATE = "PATIENT_UPDATE";
    public static final String PATIENT_DELETE = "PATIENT_DELETE";

    // Medical records
    public static final String MEDICAL_RECORD_VIEW = "MEDICAL_RECORD_VIEW";
    public static final String MEDICAL_RECORD_CREATE = "MEDICAL_RECORD_CREATE";
    public static final String MEDICAL_RECORD_UPDATE = "MEDICAL_RECORD_UPDATE";

    // Prescriptions
    public static final String PRESCRIPTION_VIEW = "PRESCRIPTION_VIEW";
    public static final String PRESCRIPTION_CREATE = "PRESCRIPTION_CREATE";
    public static final String PRESCRIPTION_UPDATE = "PRESCRIPTION_UPDATE";

    // Laboratory
    public static final String LAB_ORDER_VIEW = "LAB_ORDER_VIEW";
    public static final String LAB_ORDER_CREATE = "LAB_ORDER_CREATE";
    public static final String LAB_REPORT_VIEW = "LAB_REPORT_VIEW";
    public static final String LAB_REPORT_CREATE = "LAB_REPORT_CREATE";

    // Patient access
    public static final String PATIENT_ACCESS_REQUEST = "PATIENT_ACCESS_REQUEST";
    public static final String PATIENT_ACCESS_APPROVE = "PATIENT_ACCESS_APPROVE";
    public static final String PATIENT_ACCESS_REVOKE = "PATIENT_ACCESS_REVOKE";

    // Secure messages
    public static final String SECURE_MESSAGE_SEND = "SECURE_MESSAGE_SEND";
    public static final String SECURE_MESSAGE_READ = "SECURE_MESSAGE_READ";

    // User management
    public static final String USER_VIEW = "USER_VIEW";
    public static final String USER_CREATE = "USER_CREATE";
    public static final String USER_UPDATE = "USER_UPDATE";
    public static final String USER_DISABLE = "USER_DISABLE";

    // Security
    public static final String AUDIT_LOG_VIEW = "AUDIT_LOG_VIEW";
    public static final String SECURITY_EVENT_VIEW = "SECURITY_EVENT_VIEW";
    public static final String SECURITY_EVENT_MANAGE = "SECURITY_EVENT_MANAGE";
}