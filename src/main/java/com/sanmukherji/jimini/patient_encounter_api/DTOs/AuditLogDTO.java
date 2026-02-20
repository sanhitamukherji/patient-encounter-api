package com.sanmukherji.jimini.patient_encounter_api.DTOs;

import java.time.Instant;

public record AuditLogDTO(
        String auditId,
        String action,
        String encounterId,
        String performedBy,
        Instant performedAt,
        String details
) {}

