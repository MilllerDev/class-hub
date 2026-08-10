package com.bailon.backend.courses.infrastructure.rest.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public record CourseRequest(
        @NotBlank(message = "El nombre es obligatorio")
        @Size(min = 3, max = 80, message = "El nombre debe tener entre 3 y 80 caracteres")
        String name,
        String description,
        @NotNull(message = "Es obligatorio indicar la generación de la descripción")
        boolean useAi,
        UUID userId
) {
}
