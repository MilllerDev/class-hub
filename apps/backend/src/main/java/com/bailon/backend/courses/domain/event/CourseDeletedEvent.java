package com.bailon.backend.courses.domain.event;

import java.util.UUID;

public record CourseDeletedEvent(UUID courseId) {
}
