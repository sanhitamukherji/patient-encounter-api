package com.sanmukherji.jimini.patient_encounter_api.DTOs;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
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
    private String  updatedBy;

    @PrePersist
    protected void onCreate() {
        this.createdAt = Instant.now();
        this.updatedAt = Instant.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = Instant.now();
    }
}
