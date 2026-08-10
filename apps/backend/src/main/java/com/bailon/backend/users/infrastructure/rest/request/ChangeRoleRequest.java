package com.bailon.backend.users.infrastructure.rest.request;

import com.bailon.backend.users.domain.model.UserRole;
import jakarta.validation.constraints.NotNull;

public record ChangeRoleRequest(
        @NotNull(message = "El rol es obligatorio")
        UserRole role
) {
}
