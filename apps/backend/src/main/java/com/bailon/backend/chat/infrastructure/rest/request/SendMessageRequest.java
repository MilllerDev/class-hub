package com.bailon.backend.chat.infrastructure.rest.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.List;
import java.util.UUID;

public record SendMessageRequest(
        @NotBlank(message = "La pregunta es obligatoria")
        @Size(max = 2000, message = "La pregunta no puede superar los 2000 caracteres")
        String question,

        List<UUID> resourceIds
) {
}
