package com.bailon.backend.profile.infrastructure.rest.response;

import com.bailon.backend.users.infrastructure.rest.response.UserResponse;

import java.util.UUID;

public record ProfileResponse(
        UUID id,
        String name,
        String lastName,
        String avatarUrl,
        UserResponse user
) {
}
