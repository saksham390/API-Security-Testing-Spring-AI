package com.example.apisecurity.dto;

import java.time.Instant;

import com.example.apisecurity.entity.Role;

public record AuthResponse(
        String token,
        String username,
        Role role,
        Instant createdAt
) {
}
