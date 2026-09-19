package com.medicalsecurity.controller;

import com.medicalsecurity.service.SecureMessageService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/secure-message")
public class SecureMessageController {

    private final SecureMessageService secureMessageService;

    public SecureMessageController(
            SecureMessageService secureMessageService) {

        this.secureMessageService = secureMessageService;
    }

    @PostMapping("/create")
    public String createSecureMessage(
            @RequestParam String medicalData,
            @RequestParam String carrier) {

        return secureMessageService.createSecureMessage(
                medicalData,
                carrier
        );
    }

    @PostMapping("/read")
    public String readSecureMessage(
            @RequestBody String stegoText) {

        return secureMessageService.readSecureMessage(
                stegoText
        );
    }
}