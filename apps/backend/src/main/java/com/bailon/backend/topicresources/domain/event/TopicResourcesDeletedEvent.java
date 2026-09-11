package com.bailon.backend.topicresources.domain.event;

import java.util.UUID;

public record TopicResourcesDeletedEvent(UUID resourceId) {
}
