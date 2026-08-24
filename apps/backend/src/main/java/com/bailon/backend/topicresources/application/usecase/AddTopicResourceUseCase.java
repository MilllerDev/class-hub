package com.bailon.backend.topicresources.application.usecase;

import com.bailon.backend.topicresources.domain.model.TopicResource;

import java.util.UUID;

public interface AddTopicResourceUseCase {
    TopicResource execute(UUID userId, UUID topicId, TopicResource resource);
}
