package com.bailon.backend.chat.application.service;

import com.bailon.backend.chat.application.port.ChatMessageRepository;
import com.bailon.backend.chat.application.port.GenerateChatAnswerPort;
import com.bailon.backend.chat.application.usecase.SendMessageUseCase;
import com.bailon.backend.chat.domain.model.ChatMessage;
import com.bailon.backend.topics.application.port.TopicRepository;
import com.bailon.backend.topics.domain.exception.TopicNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class SendMessageService implements SendMessageUseCase {
    private final TopicRepository topicRepository;
    private final GenerateChatAnswerPort answerPort;
    private final ChatMessageRepository repository;

    public SendMessageService(TopicRepository topicRepository, GenerateChatAnswerPort answerPort, ChatMessageRepository repository) {
        this.topicRepository = topicRepository;
        this.answerPort = answerPort;
        this.repository = repository;
    }

    @Override
    public ChatMessage send(UUID userId, UUID topicId, String question, List<UUID> resourcesId) {
        if (!topicRepository.exists(topicId)) {
            throw new TopicNotFoundException("No se encontró el tema");
        }
        String answer = answerPort.generate(topicId, resourcesId, question);
        return repository.save(ChatMessage.ask(userId, topicId, question, answer));
    }
}