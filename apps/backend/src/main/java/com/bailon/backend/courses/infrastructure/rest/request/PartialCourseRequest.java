package com.bailon.backend.courses.infrastructure.rest.request;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record PartialCourseRequest(
        String name,
        String description,
        @NotNull(message = "Es obligatorio indicar la generación de la descripción")
        boolean useAi,
        UUID userId
) {
}
