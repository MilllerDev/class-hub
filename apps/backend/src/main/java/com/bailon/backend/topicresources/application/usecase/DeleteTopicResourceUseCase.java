package com.bailon.backend.topicresources.application.usecase;

import java.util.UUID;

public interface DeleteTopicResourceUseCase {
    void execute(UUID teacherId, UUID topicId, UUID resourceId);
}
