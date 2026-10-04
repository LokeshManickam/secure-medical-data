package com.medicalsecurity.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "patient_access_requests")
public class PatientAccessRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /*
     * The patient whose data is being requested.
     */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;

    /*
     * The healthcare user requesting access.
     */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "requester_id", nullable = false)
    private User requester;

    /*
     * Current state of the access request.
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private AccessRequestStatus status;

    /*
     * Optional reason provided by the requester.
     */
    @Column(length = 500)
    private String reason;

    /*
     * Time when the request was created.
     */
    @Column(name = "requested_at", nullable = false)
    private LocalDateTime requestedAt;

    /*
     * User who reviewed the request.
     *
     * This will normally be an ADMIN or HEAD_DOCTOR.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reviewed_by")
    private User reviewedBy;

    /*
     * Time when the request was approved or rejected.
     */
    @Column(name = "reviewed_at")
    private LocalDateTime reviewedAt;

    /*
     * Default constructor required by JPA.
     */
    public PatientAccessRequest() {
    }

    /*
     * Constructor used when creating a new access request.
     */
    public PatientAccessRequest(
            Patient patient,
            User requester,
            String reason) {

        this.patient = patient;
        this.requester = requester;
        this.reason = reason;
        this.status = AccessRequestStatus.PENDING;
        this.requestedAt = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public User getRequester() {
        return requester;
    }

    public void setRequester(User requester) {
        this.requester = requester;
    }

    public AccessRequestStatus getStatus() {
        return status;
    }

    public void setStatus(AccessRequestStatus status) {
        this.status = status;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public LocalDateTime getRequestedAt() {
        return requestedAt;
    }

    public void setRequestedAt(LocalDateTime requestedAt) {
        this.requestedAt = requestedAt;
    }

    public User getReviewedBy() {
        return reviewedBy;
    }

    public void setReviewedBy(User reviewedBy) {
        this.reviewedBy = reviewedBy;
    }

    public LocalDateTime getReviewedAt() {
        return reviewedAt;
    }

    public void setReviewedAt(LocalDateTime reviewedAt) {
        this.reviewedAt = reviewedAt;
    }
}