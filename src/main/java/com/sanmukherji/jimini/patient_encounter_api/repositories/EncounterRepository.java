package com.sanmukherji.jimini.patient_encounter_api.repositories;

import com.sanmukherji.jimini.patient_encounter_api.DTOs.EncounterEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface EncounterRepository extends JpaRepository<EncounterEntity, UUID> {
    // That's it. Nothing else needed for now.
}