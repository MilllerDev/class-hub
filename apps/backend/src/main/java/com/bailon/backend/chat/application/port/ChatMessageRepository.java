package com.bailon.backend.chat.application.port;

import com.bailon.backend.chat.domain.model.ChatMessage;

import java.util.List;
import java.util.UUID;

public interface ChatMessageRepository {
    ChatMessage save(ChatMessage message);

    boolean existsChatOwnedUser(UUID messageId, UUID topicId, UUID userId);

    List<ChatMessage> findChatOwnedUser(UUID topicId, UUID userId);

    List<ChatMessage> lastMessages(UUID userId);

    void deleteById(UUID id);
}