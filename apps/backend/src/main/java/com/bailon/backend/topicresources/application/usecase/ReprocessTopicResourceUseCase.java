package com.bailon.backend.topicresources.application.usecase;

import com.bailon.backend.topicresources.domain.model.TopicResource;

import java.util.UUID;

public interface ReprocessTopicResourceUseCase {
    TopicResource execute(UUID teacherId, UUID resourceId);
}
