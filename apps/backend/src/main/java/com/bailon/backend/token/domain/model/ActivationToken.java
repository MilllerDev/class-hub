package com.bailon.backend.token.domain.model;

import lombok.*;

import java.security.SecureRandom;
import java.time.Instant;
import java.util.Base64;
import java.util.UUID;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ActivationToken {
    public static final long EXPIRATION_SECONDS = 900;

    private UUID id;
    private String token;
    private boolean used;
    private Instant expiresAt;
    private Instant usedAt;
    private UUID userId;

    private static String generateToken() {
        byte[] bytes = new byte[32];
        new SecureRandom().nextBytes(bytes);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);
    }

    public static ActivationToken create(UUID userId) {
        return ActivationToken.builder()
                .token(generateToken())
                .used(false)
                .userId(userId)
                .expiresAt(Instant.now().plusSeconds(EXPIRATION_SECONDS))
                .build();
    }

    public boolean isExpired(Instant now) {
        return expiresAt == null || expiresAt.isBefore(now);
    }

    public void markUsed() {
        this.used = true;
        this.usedAt = Instant.now();
    }
}

