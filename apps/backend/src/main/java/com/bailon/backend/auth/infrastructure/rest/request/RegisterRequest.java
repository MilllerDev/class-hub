package com.bailon.backend.auth.infrastructure.rest.request;

import com.bailon.backend.users.infrastructure.rest.request.UserRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegisterRequest(
        @NotBlank(message = "El nombre es obligatorio")
        @Size(min = 3, max = 20, message = "El nombre debe tener de 3 a 20 carácteres")
        String name,

        @NotBlank(message = "El apellido es obligatorio")
        @Size(min = 5, max = 50, message = "El apellido debe tener de 5 a 20 carácteres")
        String lastName,
        @Valid UserRequest user
) {
}
