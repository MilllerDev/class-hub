package com.bailon.backend.profile.infrastructure.rest.request;

import jakarta.validation.constraints.Size;

public record PartialProfileRequest(
        @Size(max = 20, message = "El nombre no puede superar los 20 caracteres")
        String name,

        @Size(max = 50, message = "El apellido no puede superar los 50 caracteres")
        String lastName,

        String avatarUrl
) {
}
