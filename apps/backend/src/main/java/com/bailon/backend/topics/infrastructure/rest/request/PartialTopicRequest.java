package com.bailon.backend.topics.infrastructure.rest.request;

import java.util.UUID;

public record PartialTopicRequest(
        String title,
        UUID courseId
) {
}