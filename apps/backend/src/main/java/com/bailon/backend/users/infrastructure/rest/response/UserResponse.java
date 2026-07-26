package com.bailon.backend.users.infrastructure.rest.response;

import com.bailon.backend.users.domain.model.UserRole;

import java.util.UUID;

public record UserResponse(
        UUID id,
        String email,
        UserRole role,
        boolean enabled
) {
}
