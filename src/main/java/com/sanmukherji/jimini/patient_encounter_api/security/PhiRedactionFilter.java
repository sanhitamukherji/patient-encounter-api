package com.sanmukherji.jimini.patient_encounter_api.security;

public class PhiRedactionFilter {

    private PhiRedactionFilter() {}

    public static String redact(String message) {
        if (message == null) return null;

        // Redact patient IDs (PAT-XXX pattern)
        message = message.replaceAll("PAT-\\w+", "PAT-[REDACTED]");

        // Redact patientId in JSON payloads
        message = message.replaceAll(
                "(\"patientId\"\\s*:\\s*\")([^\"]+)(\")",
                "$1[REDACTED]$3");

        // Redact patientId in query params
        message = message.replaceAll(
                "(patientId=)([^,&\\s]+)",
                "$1[REDACTED]");

        return message;
    }
}
