package com.sanmukherji.jimini.patient_encounter_api.services;

import com.sanmukherji.jimini.patient_encounter_api.DTOs.EncounterDTO;
import com.sanmukherji.jimini.patient_encounter_api.exception.BadRequestException;
import com.sanmukherji.jimini.patient_encounter_api.models.EncounterEntity;
import com.sanmukherji.jimini.patient_encounter_api.repositories.EncounterRepository;
import com.sanmukherji.jimini.patient_encounter_api.security.CurrentUserService;
import com.sanmukherji.jimini.patient_encounter_api.security.PhiRedactionFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class EncounterService {
    private static final int DEFAULT_DATE_RANGE_DAYS = 30;

    private final EncounterRepository encounterRepository;
    private final AuditService auditService;
    private final CurrentUserService currentUserService;

    @Autowired
    public EncounterService(EncounterRepository encounterRepository, AuditService auditService, CurrentUserService currentUserService) {
        this.encounterRepository = encounterRepository;
        this.auditService = auditService;
        this.currentUserService = currentUserService;
    }

    public EncounterDTO create(EncounterDTO dto) {
        String userId = currentUserService.getCurrentUserId();
        EncounterEntity entity = toEntity(dto, userId);
        EncounterEntity saved = encounterRepository.save(entity);

        // Log the creation
        auditService.log(
                "CREATE",
                saved.getEncounterId().toString(),
                userId,
                null
        );
        return toDTO(saved);
    }

    public Optional<EncounterDTO> getById(UUID encounterId) {
        String userId = currentUserService.getCurrentUserId();
        // Log the access (even if not found)
        auditService.log(
                "READ",
                encounterId.toString(),
                userId,
                null
        );
        return encounterRepository.findById(encounterId)
                .map(this::toDTO);

    }

    public Optional<EncounterDTO> update(UUID encounterId, EncounterDTO dto) {
        String userId = currentUserService.getCurrentUserId();
        auditService.log("UPDATE", encounterId.toString(),
                userId, null);

        if (dto.encounterId() != null && !dto.encounterId().equals(encounterId.toString())) {
            throw new BadRequestException(
                    "encounterId in path and body do not match");
        }

        return encounterRepository.findById(encounterId)
                .map(existing -> {
                    // Only update fields that were provided
                    if (dto.patientId() != null) existing.setPatientId(dto.patientId());
                    if (dto.providerId() != null) existing.setProviderId(dto.providerId());
                    if (dto.encounterDate() != null) existing.setEncounterDate(dto.encounterDate());
                    if (dto.type() != null) existing.setType(dto.type());
                    if (dto.clinicalData() != null) existing.setClinicalData(dto.clinicalData());
                    existing.setUpdatedBy(userId);

                    EncounterEntity saved = encounterRepository.save(existing);

                    return toDTO(saved);
                });
    }

    public List<EncounterDTO> search(String patientId,
                                     String providerId,
                                     Instant startDate,
                                     Instant endDate) {
        String userId = currentUserService.getCurrentUserId();
        // Log the search (even if not valid)
        String auditDetails = "patientId=" + patientId + ",providerId=" + providerId + ((startDate != null)?
                ",startDate=" + startDate : "" ) + ((endDate != null)?
                ",endDate=" + endDate : "" );

        auditService.log(
                "SEARCH",
                null,
                userId,
                PhiRedactionFilter.redact(auditDetails)
        );

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
            throw new BadRequestException(
                    "At least one filter is required: patientId, providerId, or date range");
        }

        if (startDate != null && endDate != null && startDate.isAfter(endDate)) {
            throw new BadRequestException(
                    "startDate must be before endDate");
        }
    }



    // ---- Mapping helpers ----

    private EncounterEntity toEntity(EncounterDTO dto, String userId) {
        EncounterEntity entity = new EncounterEntity();
        entity.setPatientId(dto.patientId());
        entity.setProviderId(dto.providerId());
        entity.setEncounterDate(dto.encounterDate());
        entity.setType(dto.type());
        entity.setClinicalData(dto.clinicalData());
        entity.setCreatedBy(userId);
        entity.setUpdatedBy(userId); //on creation, updatedby = createdby
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
                entity.getCreatedBy(),
                entity.getUpdatedAt(),
                entity.getUpdatedBy()
        );
    }
    //overloading toDTO
    private List<EncounterDTO> toDTO(List<EncounterEntity> entities) {
        return entities.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }
}
