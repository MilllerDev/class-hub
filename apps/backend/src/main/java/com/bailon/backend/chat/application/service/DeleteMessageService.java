package com.bailon.backend.chat.application.service;

import com.bailon.backend.chat.application.port.ChatMessageRepository;
import com.bailon.backend.chat.application.usecase.DeleteMessageUseCase;
import com.bailon.backend.chat.domain.exception.ChatMessageNotFoundException;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class DeleteMessageService implements DeleteMessageUseCase {
    private final ChatMessageRepository repository;

    public DeleteMessageService(ChatMessageRepository repository) {
        this.repository = repository;
    }

    @Override
    public void delete(UUID userId, UUID topicId, UUID messageId) {
        if (!repository.existsChatOwnedUser(messageId, topicId, userId)) {
            throw new ChatMessageNotFoundException("No se pudo encontrar el chat");
        }
        repository.deleteById(messageId);
    }
}