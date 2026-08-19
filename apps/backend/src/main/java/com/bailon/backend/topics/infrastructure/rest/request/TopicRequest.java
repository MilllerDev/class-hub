package com.bailon.backend.topics.infrastructure.rest.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public record TopicRequest(
        @NotBlank(message = "El título es obligatorio")
        @Size(min = 3, max = 100, message = "El título debe tener entre 3 y 100 caracteres")
        String title,
        @NotNull(message = "Es obligatorio indicar el curso")
        UUID courseId
) {
}