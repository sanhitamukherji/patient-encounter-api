package com.sanmukherji.jimini.patient_encounter_api.controllers;

import com.sanmukherji.jimini.patient_encounter_api.DTOs.AuditLogDTO;
import com.sanmukherji.jimini.patient_encounter_api.services.AuditService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;

@RestController
@RequestMapping("/audit")
public class AuditController {

    private final AuditService auditService;

    public AuditController(AuditService auditService) {
        this.auditService = auditService;
    }

    @GetMapping("/encounters")
    public ResponseEntity<List<AuditLogDTO>> getAuditLogs(
            @RequestParam(required = false) Instant startDate,
            @RequestParam(required = false) Instant endDate) {

        return ResponseEntity.ok(auditService.search(startDate, endDate));
    }
}
