package com.bailon.backend.token.domain.model;

import lombok.*;

import java.time.Instant;
import java.util.UUID;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Token {
    private UUID id;
    private String token;
    private boolean revoked;
    private UUID userId;
    private Instant createdAt;
    private Instant expiresAt;

    public static Token create(String token, UUID userId, Instant expiresAt) {
        return Token.builder()
                .token(token)
                .revoked(false)
                .userId(userId)
                .expiresAt(expiresAt)
                .build();
    }
}
