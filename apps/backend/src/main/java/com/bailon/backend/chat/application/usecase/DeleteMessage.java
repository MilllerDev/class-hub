package com.bailon.backend.chat.application.usecase;

import java.util.UUID;

public interface DeleteMessage {
    void delete(UUID userId, UUID topicId, UUID messageId);
}
