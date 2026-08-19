package com.bailon.backend.topics.application.usecase;

import com.bailon.backend.topics.domain.model.Topic;

import java.util.UUID;

public interface UpdateTopicUseCase {
    Topic execute(UUID teacherId, UUID id, Topic topic);
}