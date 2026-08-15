package com.bailon.backend.topics.application.usecase;

import java.util.UUID;

public interface DeleteTopicUseCase {
    void execute(UUID teacherId, UUID id);
}