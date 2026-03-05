package com.sanmukherji.jimini.patient_encounter_api.security;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Component
public class CurrentUserService {

    public String getCurrentUserId() {
        ServletRequestAttributes attrs =
                (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

        if (attrs == null) {
            return "SYSTEM";
        }

        HttpServletRequest request = attrs.getRequest();
        String userId = (String) request.getAttribute("userId");
        return userId != null ? userId : "SYSTEM";
    }
}
