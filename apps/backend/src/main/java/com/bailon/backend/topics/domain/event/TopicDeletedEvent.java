package com.bailon.backend.topics.domain.event;

import java.util.UUID;

public record TopicDeletedEvent(UUID topicId) {
}
