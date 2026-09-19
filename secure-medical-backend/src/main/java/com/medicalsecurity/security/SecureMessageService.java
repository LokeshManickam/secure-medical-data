package com.medicalsecurity.service;

import com.medicalsecurity.encryption.AesEncryptionService;
import com.medicalsecurity.steganography.TextSteganographyService;
import org.springframework.stereotype.Service;

@Service
public class SecureMessageService {

    private final AesEncryptionService encryptionService;
    private final TextSteganographyService steganographyService;

    public SecureMessageService(
            AesEncryptionService encryptionService,
            TextSteganographyService steganographyService) {

        this.encryptionService = encryptionService;
        this.steganographyService = steganographyService;
    }

    // Encrypt medical data and hide it inside carrier text.
    public String createSecureMessage(
            String medicalData,
            String carrierText) {

        // Step 1: Encrypt the medical information.
        String encryptedData =
                encryptionService.encrypt(medicalData);

        // Step 2: Hide encrypted data inside carrier text.
        return steganographyService.hide(
                carrierText,
                encryptedData
        );
    }

    // Extract encrypted data and decrypt it.
    public String readSecureMessage(String stegoText) {

        // Step 1: Extract the hidden encrypted data.
        String encryptedData =
                steganographyService.extract(stegoText);

        // Step 2: Decrypt the extracted data.
        return encryptionService.decrypt(encryptedData);
    }
}