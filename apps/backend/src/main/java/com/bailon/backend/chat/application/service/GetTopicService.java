package com.bailon.backend.chat.application.service;

import com.bailon.backend.chat.application.port.ChatMessageRepository;
import com.bailon.backend.chat.application.usecase.ListChatUseCase;
import com.bailon.backend.chat.domain.model.ChatMessage;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class GetTopicService implements ListChatUseCase {
    private final ChatMessageRepository repository;

    public GetTopicService(ChatMessageRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<ChatMessage> list(UUID userId, UUID topicId) {
        return repository.findChatOwnedUser(topicId, userId);
    }

    @Override
    public List<ChatMessage> last(UUID userId) {
        return repository.lastMessages(userId);
    }
}