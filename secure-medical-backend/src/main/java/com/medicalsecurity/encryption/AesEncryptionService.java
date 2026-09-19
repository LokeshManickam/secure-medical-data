package com.medicalsecurity.encryption;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.Cipher;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.Base64;

@Service
public class AesEncryptionService {

    private static final String AES = "AES";
    private static final String AES_GCM = "AES/GCM/NoPadding";

    // 12 bytes is the recommended nonce size for GCM.
    private static final int IV_LENGTH = 12;

    // 128-bit authentication tag.
    private static final int TAG_LENGTH = 128;

    private final SecretKeySpec secretKey;

    public AesEncryptionService(
            @Value("${encryption.secret}") String secret) {

        try {
            java.security.MessageDigest digest =
                    java.security.MessageDigest.getInstance("SHA-256");

            byte[] keyBytes =
                    digest.digest(secret.getBytes(StandardCharsets.UTF_8));

            this.secretKey =
                    new SecretKeySpec(keyBytes, AES);

        } catch (Exception exception) {

            throw new IllegalStateException(
                    "Unable to create AES key",
                    exception
            );
        }
    }

    public String encrypt(String plainText) {

        try {
            byte[] iv = new byte[IV_LENGTH];

            SecureRandom secureRandom = new SecureRandom();
            secureRandom.nextBytes(iv);

            Cipher cipher =
                    Cipher.getInstance(AES_GCM);

            GCMParameterSpec parameterSpec =
                    new GCMParameterSpec(
                            TAG_LENGTH,
                            iv
                    );

            cipher.init(
                    Cipher.ENCRYPT_MODE,
                    secretKey,
                    parameterSpec
            );

            byte[] encryptedBytes =
                    cipher.doFinal(
                            plainText.getBytes(StandardCharsets.UTF_8)
                    );

            // Store IV + encrypted data together.
            byte[] result =
                    new byte[iv.length + encryptedBytes.length];

            System.arraycopy(
                    iv,
                    0,
                    result,
                    0,
                    iv.length
            );

            System.arraycopy(
                    encryptedBytes,
                    0,
                    result,
                    iv.length,
                    encryptedBytes.length
            );

            return Base64.getEncoder()
                    .encodeToString(result);

        } catch (Exception exception) {

            throw new IllegalStateException(
                    "Encryption failed",
                    exception
            );
        }
    }

    public String decrypt(String encryptedText) {

        try {
            byte[] combined =
                    Base64.getDecoder()
                            .decode(encryptedText);

            byte[] iv =
                    new byte[IV_LENGTH];

            byte[] encryptedBytes =
                    new byte[combined.length - IV_LENGTH];

            System.arraycopy(
                    combined,
                    0,
                    iv,
                    0,
                    IV_LENGTH
            );

            System.arraycopy(
                    combined,
                    IV_LENGTH,
                    encryptedBytes,
                    0,
                    encryptedBytes.length
            );

            Cipher cipher =
                    Cipher.getInstance(AES_GCM);

            GCMParameterSpec parameterSpec =
                    new GCMParameterSpec(
                            TAG_LENGTH,
                            iv
                    );

            cipher.init(
                    Cipher.DECRYPT_MODE,
                    secretKey,
                    parameterSpec
            );

            byte[] decryptedBytes =
                    cipher.doFinal(encryptedBytes);

            return new String(
                    decryptedBytes,
                    StandardCharsets.UTF_8
            );

        } catch (Exception exception) {

            throw new IllegalStateException(
                    "Decryption failed",
                    exception
            );
        }
    }
}