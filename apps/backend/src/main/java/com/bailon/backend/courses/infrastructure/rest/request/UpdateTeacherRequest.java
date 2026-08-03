package com.bailon.backend.courses.infrastructure.rest.request;

import java.util.UUID;

public record UpdateTeacherRequest(
        UUID userId
) {
}
