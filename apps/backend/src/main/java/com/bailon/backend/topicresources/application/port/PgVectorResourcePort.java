package com.bailon.backend.topicresources.application.port;

import com.bailon.backend.topicresources.domain.model.TopicResource;

import java.util.List;
import java.util.UUID;

public interface PgVectorResourcePort {
    void process(TopicResource resource, UUID courseId, UUID userId);

    List<String> searchContext(String query, UUID topicId, List<UUID> topicResourceIds);
}
