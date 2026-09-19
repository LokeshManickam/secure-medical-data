package com.medicalsecurity.steganography;

import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;

@Service
public class TextSteganographyService {

    // Zero-width characters used to represent binary data.
    private static final char ZERO = '\u200B';
    private static final char ONE = '\u200C';

    // Marker tells us that hidden data exists.
    private static final String START_MARKER = "\u200D";
    private static final String END_MARKER = "\u200D";

    /**
     * Hides a secret message inside normal carrier text.
     */
    public String hide(String carrierText, String secretMessage) {

        byte[] bytes =
                secretMessage.getBytes(StandardCharsets.UTF_8);

        StringBuilder binary = new StringBuilder();

        // Convert every byte into 8 binary bits.
        for (byte currentByte : bytes) {

            for (int bit = 7; bit >= 0; bit--) {

                int value = (currentByte >> bit) & 1;

                if (value == 0) {
                    binary.append(ZERO);
                } else {
                    binary.append(ONE);
                }
            }
        }

        return carrierText
                + START_MARKER
                + binary
                + END_MARKER;
    }

    /**
     * Extracts the hidden message from stego text.
     */
    public String extract(String stegoText) {

        int start =
                stegoText.indexOf(START_MARKER);

        int end =
                stegoText.lastIndexOf(END_MARKER);

        if (start == -1 || end == -1 || end <= start) {

            throw new IllegalArgumentException(
                    "No hidden message found"
            );
        }

        String hiddenBits =
                stegoText.substring(
                        start + START_MARKER.length(),
                        end
                );

        if (hiddenBits.length() % 8 != 0) {

            throw new IllegalArgumentException(
                    "Invalid hidden message"
            );
        }

        byte[] bytes =
                new byte[hiddenBits.length() / 8];

        for (int i = 0; i < bytes.length; i++) {

            int value = 0;

            for (int bit = 0; bit < 8; bit++) {

                char current =
                        hiddenBits.charAt(i * 8 + bit);

                value <<= 1;

                if (current == ONE) {
                    value |= 1;
                } else if (current != ZERO) {

                    throw new IllegalArgumentException(
                            "Invalid steganography data"
                    );
                }
            }

            bytes[i] = (byte) value;
        }

        return new String(
                bytes,
                StandardCharsets.UTF_8
        );
    }
}