package com.bailon.backend.topics.application.usecase;

import com.bailon.backend.topics.domain.model.Topic;

import java.util.UUID;

public interface CreateTopicUseCase {
    Topic execute(UUID teacherId, Topic topic);
}