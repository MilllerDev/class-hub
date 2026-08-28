package com.bailon.backend.topicresources.domain.event;

import java.util.List;
import java.util.UUID;

public record TopicResourcesDeletedEvent(List<UUID> resourceIds) {
}
