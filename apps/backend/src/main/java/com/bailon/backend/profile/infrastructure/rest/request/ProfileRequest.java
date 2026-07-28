package com.bailon.backend.profile.infrastructure.rest.request;

import jakarta.validation.constraints.NotBlank;

public record ProfileRequest(
        @NotBlank(message = "El nombre es obligatorio") String name,
        @NotBlank(message = "El apellido es obligatorio") String lastName,
        String avatarUrl
) {
}
