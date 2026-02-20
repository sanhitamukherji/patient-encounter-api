package com.sanmukherji.jimini.patient_encounter_api.repositories;

import com.sanmukherji.jimini.patient_encounter_api.models.AuditLogEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Repository
public interface AuditLogRepository extends JpaRepository<AuditLogEntity, UUID> {

    List<AuditLogEntity> findByPerformedAtBetween(Instant startDate, Instant endDate);

    List<AuditLogEntity> findByEncounterId(String encounterId);
}
