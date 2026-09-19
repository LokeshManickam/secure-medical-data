package com.medicalsecurity.dto;

public record LoginRequest(
        String username,
        String password
) {
}