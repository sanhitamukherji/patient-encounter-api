package com.sanmukherji.jimini.patient_encounter_api.repositories;

import com.sanmukherji.jimini.patient_encounter_api.models.EncounterEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Repository
public interface EncounterRepository extends JpaRepository<EncounterEntity, UUID> {
    List<EncounterEntity> findByPatientId(String patientId);

    List<EncounterEntity> findByProviderId(String providerId);

    List<EncounterEntity> findByEncounterDateBetween(Instant startDate, Instant endDate);

    List<EncounterEntity> findByPatientIdAndEncounterDateBetween(
            String patientId, Instant startDate, Instant endDate);

    List<EncounterEntity> findByProviderIdAndEncounterDateBetween(
            String providerId, Instant startDate, Instant endDate);
}