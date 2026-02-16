package com.sanmukherji.jimini.patient_encounter_api.models;

import com.sanmukherji.jimini.patient_encounter_api.enums.EncounterType;
import jakarta.persistence.*;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "encounters")
public class EncounterEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID encounterId;

    @Column(nullable = false)
    private String patientId;

    @Column(nullable = false)
    private String providerId;

    @Column(nullable = false)
    private Instant encounterDate;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)    // Stores "FOLLOW_UP" as text in DB
    private EncounterType type;

    @Column(columnDefinition = "TEXT")
    private String clinicalData;

    @Column(nullable = false, updatable = false)
    private Instant createdAt;

    @Column(nullable = false, updatable = false)
    private String createdBy;

    @Column(nullable = false)
    private Instant updatedAt;

    @Column(nullable = false)
    private String updatedBy;

    @PrePersist
    protected void onCreate() {
        this.createdAt = Instant.now();
        this.updatedAt = Instant.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = Instant.now();
    }

//LOMBOK is effing up; manually adding getterssetter
    // Getters
    public UUID getEncounterId() { return encounterId; }
    public String getPatientId() { return patientId; }
    public String getProviderId() { return providerId; }
    public Instant getEncounterDate() { return encounterDate; }
    public EncounterType getType() { return type; }
    public String getClinicalData() { return clinicalData; }
    public Instant getCreatedAt() { return createdAt; }
    public Instant getUpdatedAt() { return updatedAt; }
    public String getCreatedBy() { return createdBy; }
    public String getUpdatedBy() { return updatedBy; }

    // Setters
    public void setPatientId(String patientId) { this.patientId = patientId; }
    public void setProviderId(String providerId) { this.providerId = providerId; }
    public void setEncounterDate(Instant encounterDate) { this.encounterDate = encounterDate; }
    public void setType(EncounterType encounterType) { this.type = encounterType; }
    public void setClinicalData(String clinicalData) { this.clinicalData = clinicalData; }
    public void setCreatedBy(String createdBy) { this.createdBy = createdBy; }
    public void setUpdatedBy(String updatedBy) { this.updatedBy = updatedBy; }
}
