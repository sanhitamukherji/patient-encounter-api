package com.sanmukherji.jimini.patient_encounter_api.DTOs;

import java.time.LocalDate;

public record EncounterDTO(String encounterId, String patientId, String provideId, LocalDate encounterDate, EncounterType type, String clinicalData,
                           LocalDate curr_date, String  created_by) {
}

