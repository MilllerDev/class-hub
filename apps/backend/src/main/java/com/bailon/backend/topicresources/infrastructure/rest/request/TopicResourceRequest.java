package com.bailon.backend.topicresources.infrastructure.rest.request;

import com.bailon.backend.topicresources.domain.model.ResourceType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public record TopicResourceRequest(
        @NotBlank(message = "El nombre del archivo es obligatorio")
        @Size(max = 255, message = "El nombre del archivo no puede superar los 255 caracteres")
        String fileName,

        @NotBlank(message = "La URL del archivo es obligatoria")
        String fileUrl,

        @NotNull(message = "El tipo de archivo es obligatorio")
        ResourceType fileType,

        @NotNull(message = "Es necesario que esté asociado a un tema")
        UUID topicId
) {
}
