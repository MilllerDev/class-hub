package com.bailon.backend.courses.infrastructure.rest.response;

import java.util.UUID;

public record CourseResponse(
        UUID id,
        String slug,
        String name,
        String description,
        UUID userId,
        boolean enabled
) {
}
