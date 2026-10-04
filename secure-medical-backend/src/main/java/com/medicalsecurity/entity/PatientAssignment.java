package com.medicalsecurity.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "patient_assignments")

public class PatientAssignment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /*
     * The patient who is being assigned.
     */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "patient_id",
            nullable = false
    )
    private Patient patient;

    /*
     * The healthcare user receiving the assignment.
     */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "user_id",
            nullable = false
    )
    private User user;

    /*
     * The user who created this assignment.
     *
     * For example:
     * HEAD_DOCTOR assigns doctor01 to PT-001.
     */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "assigned_by",
            nullable = false
    )
    private User assignedBy;

    /*
     * Date and time when the assignment was created.
     */
    @Column(
            name = "assigned_at",
            nullable = false
    )
    private LocalDateTime assignedAt;

    /*
     * Allows an assignment to be deactivated
     * without deleting the historical record.
     */
    @Column(
            nullable = false
    )
    private boolean active = true;

    public PatientAssignment() {
    }

    public PatientAssignment(
            Patient patient,
            User user,
            User assignedBy
    ) {
        this.patient = patient;
        this.user = user;
        this.assignedBy = assignedBy;
        this.assignedAt = LocalDateTime.now();
        this.active = true;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getAssignedBy() {
        return assignedBy;
    }

    public void setAssignedBy(User assignedBy) {
        this.assignedBy = assignedBy;
    }

    public LocalDateTime getAssignedAt() {
        return assignedAt;
    }

    public void setAssignedAt(LocalDateTime assignedAt) {
        this.assignedAt = assignedAt;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}