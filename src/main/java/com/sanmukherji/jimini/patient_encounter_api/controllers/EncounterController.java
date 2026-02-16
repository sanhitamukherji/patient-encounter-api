package com.sanmukherji.jimini.patient_encounter_api.controllers;

import com.sanmukherji.jimini.patient_encounter_api.DTOs.EncounterDTO;
import com.sanmukherji.jimini.patient_encounter_api.services.EncounterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/encounter")
public class EncounterController
{
    private final EncounterService encounterService;

    @Autowired
    public EncounterController(EncounterService encounterService) {
        this.encounterService = encounterService;
    }

    @PostMapping
    public ResponseEntity<EncounterDTO> create(@RequestBody EncounterDTO encounter) {

        //persist
        EncounterDTO created = encounterService.create(encounter);

        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping("/{encounterId}")
    public ResponseEntity<EncounterDTO> getById(@PathVariable UUID encounterId) {

        //if doesnt exist return empty bidy with objectNot found 404 error;
        return encounterService.getById(encounterId)
                .map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

//    @GetMapping()
//    public ResponseEntity<List<EncounterDTO>> getAll(
//
//    @RequestParam(required = false) String patientId,
//    @RequestParam(required = false) String providerId,
//    @RequestParam(required = false) String encounterType,
//    @RequestParam(required = false)
//    Instant startDate,
//    @RequestParam(required = false) Instant endDate) {
//
//        //if doesnt exist return empty bidy with objectNot exists error;
//        return encounterService.getALl(encounterId);
//
//    }
}
