package com.medicalsecurity.controller;

import com.medicalsecurity.encryption.AesEncryptionService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/encryption")
public class EncryptionController {

    private final AesEncryptionService encryptionService;

    public EncryptionController(
            AesEncryptionService encryptionService) {

        this.encryptionService = encryptionService;
    }

    @PostMapping("/encrypt")
    public String encrypt(@RequestBody String plainText) {

        return encryptionService.encrypt(plainText);
    }

    @PostMapping("/decrypt")
    public String decrypt(@RequestBody String encryptedText) {

        return encryptionService.decrypt(encryptedText);
    }
}