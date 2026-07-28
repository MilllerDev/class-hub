package com.bailon.backend.auth.infrastructure.rest.request;

import com.bailon.backend.users.infrastructure.rest.request.UserRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

public record RegisterRequest(
        @NotBlank(message = "El nombre es obligatorio") String name,
        @NotBlank(message = "El apellido es obligatorio") String lastName,
        @Valid UserRequest user
) {
}
