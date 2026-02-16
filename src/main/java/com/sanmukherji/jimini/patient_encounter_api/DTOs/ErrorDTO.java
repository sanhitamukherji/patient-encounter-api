package com.sanmukherji.jimini.patient_encounter_api.DTOs;

public record ErrorDTO(
        int status,
        String message
) {}