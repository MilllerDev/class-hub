package com.bailon.backend.token.domain.model;

import java.time.Instant;

public record JwtData(
        String token,
        Instant expiresAt
) {
}
