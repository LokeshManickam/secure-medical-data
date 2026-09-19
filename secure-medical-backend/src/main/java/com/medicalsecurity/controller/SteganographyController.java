package com.medicalsecurity.controller;

import com.medicalsecurity.steganography.TextSteganographyService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/steganography")
public class SteganographyController {

    private final TextSteganographyService steganographyService;

    public SteganographyController(
            TextSteganographyService steganographyService) {

        this.steganographyService = steganographyService;
    }

    @PostMapping("/hide")
    public String hide(
            @RequestParam String carrier,
            @RequestParam String secret) {

        return steganographyService.hide(
                carrier,
                secret
        );
    }

    @PostMapping("/extract")
    public String extract(
            @RequestBody String stegoText) {

        return steganographyService.extract(
                stegoText
        );
    }
}