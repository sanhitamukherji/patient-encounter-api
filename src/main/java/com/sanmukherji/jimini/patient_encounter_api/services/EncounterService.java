package com.sanmukherji.jimini.patient_encounter_api.services;

import com.sanmukherji.jimini.patient_encounter_api.DTOs.EncounterDTO;
import com.sanmukherji.jimini.patient_encounter_api.models.EncounterEntity;
import com.sanmukherji.jimini.patient_encounter_api.repositories.EncounterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
public class EncounterService {
    private static final int DEFAULT_DATE_RANGE_DAYS = 30;
    private final EncounterRepository encounterRepository;

    @Autowired
    public EncounterService(EncounterRepository encounterRepository) {
        this.encounterRepository = encounterRepository;
    }

    public EncounterDTO create(EncounterDTO dto) {
        EncounterEntity entity = toEntity(dto);
        EncounterEntity saved = encounterRepository.save(entity);
        return toDTO(saved);
    }

    public Optional<EncounterDTO> getById(UUID encounterId) {
        return encounterRepository.findById(encounterId)
                .map(this::toDTO);

    }

    public List<EncounterDTO> search(String patientId,
                                     String providerId,
                                     Instant startDate,
                                     Instant endDate) {

        validateSearchParams(patientId, providerId, startDate, endDate);

        // Apply sensible date defaults
        if (startDate != null && endDate == null) {
            endDate = Instant.now();
        }
        if (endDate != null && startDate == null) {
            startDate = endDate.minus(DEFAULT_DATE_RANGE_DAYS, ChronoUnit.DAYS);
        }

        if (patientId != null && startDate != null) {
            return toDTO(encounterRepository.findByPatientIdAndEncounterDateBetween(
                    patientId, startDate, endDate));
        }

        if (patientId != null) {
            return toDTO(encounterRepository.findByPatientId(patientId));
        }

        if (providerId != null && startDate != null) {
            return toDTO(encounterRepository.findByProviderIdAndEncounterDateBetween(
                    providerId, startDate, endDate));
        }

        if (providerId != null) {
            return toDTO(encounterRepository.findByProviderId(providerId));
        }

        return toDTO(encounterRepository.findByEncounterDateBetween(startDate, endDate));
    }

    private void validateSearchParams(String patientId,
                                      String providerId,
                                      Instant startDate,
                                      Instant endDate) {

        if (patientId == null && providerId == null && startDate == null && endDate == null) {
            throw new IllegalArgumentException(
                    "At least one filter is required: patientId, providerId, or date range");
        }

        if (startDate != null && endDate != null && startDate.isAfter(endDate)) {
            throw new IllegalArgumentException(
                    "startDate must be before endDate");
        }
    }



    // ---- Mapping helpers ----

    private EncounterEntity toEntity(EncounterDTO dto) {
        EncounterEntity entity = new EncounterEntity();
        entity.setPatientId(dto.patientId());
        entity.setProviderId(dto.providerId());
        entity.setEncounterDate(dto.encounterDate());
        entity.setType(dto.type());
        entity.setClinicalData(dto.clinicalData());
        entity.setCreatedBy(dto.createdBy());
        return entity;
    }

    // Private helper: Entity → DTO
    private EncounterDTO toDTO(EncounterEntity entity) {
        return new EncounterDTO(
                entity.getEncounterId().toString(),
                entity.getPatientId(),
                entity.getProviderId(),
                entity.getEncounterDate(),
                entity.getType(),
                entity.getClinicalData(),
                entity.getCreatedAt(),
                entity.getCreatedBy()
        );
    }
    //overloading toDTO
    private List<EncounterDTO> toDTO(List<EncounterEntity> entities) {
        return entities.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }
}
