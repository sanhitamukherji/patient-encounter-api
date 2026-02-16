package com.sanmukherji.jimini.patient_encounter_api.controllers;

import com.sanmukherji.jimini.patient_encounter_api.DTOs.EncounterDTO;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/encounter")
public class EncounterController
{

    public boolean create(EncounterDTO encounter) {
        return false;
    }
}
