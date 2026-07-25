package com.bailon.backend.token.domain.model;

import com.bailon.backend.users.domain.model.User;
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

    public static ActivationToken create(User user) {
        return ActivationToken.builder()
                .token(generateToken())
                .used(false)
                .userId(user.getId())
                .build();
    }
}

