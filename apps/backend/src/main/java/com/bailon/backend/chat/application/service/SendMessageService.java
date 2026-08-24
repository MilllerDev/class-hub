package com.bailon.backend.chat.application.service;

import com.bailon.backend.chat.application.port.ChatMessageRepository;
import com.bailon.backend.chat.application.usecase.SendMessage;
import com.bailon.backend.chat.domain.model.ChatMessage;
import com.bailon.backend.ragquery.application.usecase.MakeQuery;
import com.bailon.backend.topics.application.port.TopicRepository;
import com.bailon.backend.topics.domain.exception.TopicNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class SendMessageService implements SendMessage {
    private final TopicRepository topicRepository;
    private final MakeQuery makeQuery;
    private final ChatMessageRepository repository;

    public SendMessageService(TopicRepository topicRepository, MakeQuery makeQuery, ChatMessageRepository repository) {
        this.topicRepository = topicRepository;
        this.makeQuery = makeQuery;
        this.repository = repository;
    }

    @Override
    public ChatMessage send(UUID userId, UUID topicId, String question, List<UUID> resourcesId) {
        if (!topicRepository.exists(topicId)) {
            throw new TopicNotFoundException("No se encontró el tema");
        }
        String answer = makeQuery.makeQuery(topicId, resourcesId, question);
        return repository.save(ChatMessage.ask(userId, topicId, question, answer));
    }
}
