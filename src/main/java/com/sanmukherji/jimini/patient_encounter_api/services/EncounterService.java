package com.sanmukherji.jimini.patient_encounter_api.services;

import com.sanmukherji.jimini.patient_encounter_api.DTOs.EncounterDTO;
import com.sanmukherji.jimini.patient_encounter_api.models.EncounterEntity;
import com.sanmukherji.jimini.patient_encounter_api.repositories.EncounterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class EncounterService {

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
}
