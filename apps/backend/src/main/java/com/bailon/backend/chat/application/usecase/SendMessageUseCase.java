package com.bailon.backend.chat.application.usecase;

import com.bailon.backend.chat.domain.model.ChatMessage;

import java.util.List;
import java.util.UUID;

public interface SendMessageUseCase {
    ChatMessage send(UUID userId, UUID topicId, String question, List<UUID> resourceIds);
}