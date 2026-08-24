package com.bailon.backend.chat.infrastructure.persistence;

import com.bailon.backend.chat.application.port.ChatMessageRepository;
import com.bailon.backend.chat.domain.model.ChatMessage;
import com.bailon.backend.chat.infrastructure.mapper.ChatMessageMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public class ChatMessageRepositoryAdapter implements ChatMessageRepository {
    private final ChatMessageRepositoryJpa repositoryJpa;
    private final ChatMessageMapper mapper;

    public ChatMessageRepositoryAdapter(ChatMessageRepositoryJpa repositoryJpa, ChatMessageMapper mapper) {
        this.repositoryJpa = repositoryJpa;
        this.mapper = mapper;
    }

    @Override
    public ChatMessage save(ChatMessage message) {
        ChatMessageEntity entity = mapper.toEntity(message);
        return mapper.toDomain(repositoryJpa.save(entity));
    }

    @Override
    public boolean existsChatOwnedUser(UUID messageId, UUID topicId, UUID userId) {
        return repositoryJpa.existsByIdAndTopicIdAndUserId(messageId, topicId, userId);
    }

    @Override
    public List<ChatMessage> findChatOwnedUser(UUID topicId, UUID userId) {
        return repositoryJpa.findByTopicIdAndUserIdOrderByCreatedAtAsc(topicId, userId).stream()
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    public void deleteById(UUID id) {
        repositoryJpa.deleteById(id);
    }
}
