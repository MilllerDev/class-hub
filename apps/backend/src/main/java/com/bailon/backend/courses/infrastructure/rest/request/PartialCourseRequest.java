package com.bailon.backend.courses.infrastructure.rest.request;

import java.util.UUID;

public record PartialCourseRequest(
        String name,
        String description,
        UUID userId
) {
}
