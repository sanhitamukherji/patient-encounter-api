package com.sanmukherji.jimini.patient_encounter_api.DTOs;

import com.sanmukherji.jimini.patient_encounter_api.enums.EncounterType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.Instant;

public record EncounterDTO(
        String encounterId,
        @NotBlank(message = "patientId is required")
        String patientId,

        @NotBlank(message = "providerId is required")
        String providerId,

        @NotNull(message = "encounterDate is required")
        Instant encounterDate,

        @NotNull(message = "type is required")
        EncounterType type,

        String clinicalData,

        Instant createdAt,

        @NotBlank(message = "createdBy is required")
        String createdBy,
        Instant updatedAt,

        String updatedBy) {
}