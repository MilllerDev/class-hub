package com.bailon.backend.chat.application.service;

import com.bailon.backend.chat.application.port.ChatMessageRepository;
import com.bailon.backend.chat.application.usecase.GetTopicMessages;
import com.bailon.backend.chat.domain.model.ChatMessage;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class GetTopicMessagesService implements GetTopicMessages {
    private final ChatMessageRepository repository;

    public GetTopicMessagesService(ChatMessageRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<ChatMessage> list(UUID userId, UUID topicId) {
        return repository.findChatOwnedUser(topicId, userId);
    }
}
