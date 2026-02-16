package com.sanmukherji.jimini.patient_encounter_api.DTOs;

import com.sanmukherji.jimini.patient_encounter_api.enums.EncounterType;

import java.time.Instant;

public record EncounterDTO(String encounterId, String patientId, String providerId, Instant encounterDate, EncounterType type, String clinicalData,
                           Instant createdAt, String createdBy) {
}