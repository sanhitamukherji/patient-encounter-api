package com.sanmukherji.jimini.patient_encounter_api.DTOs;

import jakarta.persistence.Entity;

import java.time.LocalDate;

@Entity
public class EncounterEntity {

    String encounterId;
    String patientId;
    String provideId;
    LocalDate encounterDate;
    EncounterType type;
    String clinicalData;
    LocalDate curr_date;
    String  created_by;
}
