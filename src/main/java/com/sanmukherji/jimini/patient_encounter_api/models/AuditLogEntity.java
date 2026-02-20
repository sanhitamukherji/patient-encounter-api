package com.sanmukherji.jimini.patient_encounter_api.models;

import jakarta.persistence.*;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "audit_logs")
public class AuditLogEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID auditId;

    @Column(nullable = false)
    private String action;

    private String encounterId;

    @Column(nullable = false)
    private String performedBy;

    @Column(nullable = false)
    private Instant performedAt;

    @Column(columnDefinition = "TEXT")
    private String details;

    public AuditLogEntity() {}

    @PrePersist
    protected void onCreate() {
        this.performedAt = Instant.now();
    }

    // Getters
    public UUID getAuditId() { return auditId; }
    public String getAction() { return action; }
    public String getEncounterId() { return encounterId; }
    public String getPerformedBy() { return performedBy; }
    public Instant getPerformedAt() { return performedAt; }
    public String getDetails() { return details; }

    // Setters
    public void setAction(String action) { this.action = action; }
    public void setEncounterId(String encounterId) { this.encounterId = encounterId; }
    public void setPerformedBy(String performedBy) { this.performedBy = performedBy; }
    public void setDetails(String details) { this.details = details; }
}

