package com.bailon.backend.users.infrastructure.rest.request;

import jakarta.validation.constraints.NotNull;

public record ChangeAccountStatusRequest(
        @NotNull(message = "El estado es obligatorio") boolean enabled
) {
}
