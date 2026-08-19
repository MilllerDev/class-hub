package com.bailon.backend.topics.application.usecase;

import com.bailon.backend.topics.domain.model.Topic;
import com.bailon.backend.topics.domain.model.TopicOrder;
import com.bailon.backend.users.domain.model.UserRole;

import java.util.List;
import java.util.UUID;

public interface GetTopicsUseCase {
    Topic one(UUID id, UUID userId, UserRole role);

    List<Topic> list(String slug, UUID userId, UserRole role, TopicOrder order);
}