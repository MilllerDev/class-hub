package com.bailon.backend.config.security.jwt;

import java.time.Instant;

public record JwtData(
        String token,
        Instant expiresAt
) {
}
