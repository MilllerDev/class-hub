package com.bailon.backend.topicresources.application.usecase;

import com.bailon.backend.topicresources.domain.model.TopicResource;
import com.bailon.backend.users.domain.model.UserRole;

import java.util.List;
import java.util.UUID;

public interface GetTopicResourcesUseCase {
    List<TopicResource> list(UUID topicId, UUID userId, UserRole role);
}
