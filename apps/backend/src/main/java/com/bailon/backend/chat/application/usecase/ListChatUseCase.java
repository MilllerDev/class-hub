package com.bailon.backend.chat.application.usecase;

import com.bailon.backend.chat.domain.model.ChatMessage;

import java.util.List;
import java.util.UUID;

public interface ListChatUseCase {
    List<ChatMessage> list(UUID userId, UUID topicId);

    List<ChatMessage> last(UUID userId);
}