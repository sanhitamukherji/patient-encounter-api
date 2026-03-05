package com.sanmukherji.jimini.patient_encounter_api.controllers;

import com.sanmukherji.jimini.patient_encounter_api.security.JwtService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final JwtService jwtService;

    public AuthController(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @PostMapping("/token")
    public ResponseEntity<Map<String, String>> generateToken(
            @RequestParam String userId) {
        String token = jwtService.generateToken(userId);
        return ResponseEntity.ok(Map.of("token", token));
    }
}
