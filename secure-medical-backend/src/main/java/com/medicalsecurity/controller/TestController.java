package com.medicalsecurity.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.access.prepost.PreAuthorize;

@RestController
@RequestMapping("/api/test")
public class TestController {

    @GetMapping("/secure")
    public String secureEndpoint() {
        return "JWT authentication is working!";
    }
    @GetMapping("/patient-view")
    @PreAuthorize("hasAuthority('PATIENT_VIEW')")
    public String patientViewTest() {
        return "PATIENT_VIEW permission granted";
    }
    @GetMapping("/authorities")
    public String authoritiesTest(
            org.springframework.security.core.Authentication authentication) {

        return authentication.getAuthorities().toString();
    }
}
