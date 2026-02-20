package com.sanmukherji.jimini.patient_encounter_api.services;

import com.sanmukherji.jimini.patient_encounter_api.DTOs.AuditLogDTO;
import com.sanmukherji.jimini.patient_encounter_api.exception.BadRequestException;
import com.sanmukherji.jimini.patient_encounter_api.models.AuditLogEntity;
import com.sanmukherji.jimini.patient_encounter_api.repositories.AuditLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AuditService {

    private static final int DEFAULT_AUDIT_RANGE_DAYS = 90;

    private final AuditLogRepository auditLogRepository;

    @Autowired
    public AuditService(AuditLogRepository auditLogRepository) {
        this.auditLogRepository = auditLogRepository;
    }

    // Called internally by other services
    public void log(String action, String encounterId, String performedBy, String details) {
        AuditLogEntity entity = new AuditLogEntity();
        entity.setAction(action);
        entity.setEncounterId(encounterId);
        entity.setPerformedBy(performedBy);
        entity.setDetails(details);
        auditLogRepository.save(entity);
    }

    // Called by the audit controller
    public List<AuditLogDTO> search(Instant startDate, Instant endDate) {

        // Default: last 30 days if no dates provided
        if (startDate == null && endDate == null) {
            endDate = Instant.now();
            startDate = endDate.minus(DEFAULT_AUDIT_RANGE_DAYS, ChronoUnit.DAYS);
        }
        if (startDate != null && endDate == null) {
            endDate = Instant.now();
        }
        if (endDate != null && startDate == null) {
            startDate = endDate.minus(DEFAULT_AUDIT_RANGE_DAYS, ChronoUnit.DAYS);
        }

        if (startDate.isAfter(endDate)) {
            throw new BadRequestException("startDate must be before endDate");
        }

        return auditLogRepository.findByPerformedAtBetween(startDate, endDate)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    private AuditLogDTO toDTO(AuditLogEntity entity) {
        return new AuditLogDTO(
                entity.getAuditId().toString(),
                entity.getAction(),
                entity.getEncounterId(),
                entity.getPerformedBy(),
                entity.getPerformedAt(),
                entity.getDetails()
        );
    }
}

