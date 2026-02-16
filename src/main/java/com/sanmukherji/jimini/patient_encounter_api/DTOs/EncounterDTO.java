package com.sanmukherji.jimini.patient_encounter_api.DTOs;

import java.time.Instant;
import java.time.LocalDate;

public record EncounterDTO(String encounterId, String patientId, String providerId, Instant encounterDate, EncounterType type, String clinicalData,
                           Instant createdAt, String createdBy) {
}