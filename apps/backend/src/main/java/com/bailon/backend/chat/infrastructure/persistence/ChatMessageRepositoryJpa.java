package com.bailon.backend.chat.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ChatMessageRepositoryJpa extends JpaRepository<ChatMessageEntity, UUID> {
    List<ChatMessageEntity> findByTopicIdAndUserIdOrderByCreatedAtAsc(UUID topicId, UUID userId);

    boolean existsByIdAndTopicIdAndUserId(UUID id, UUID topicId, UUID userId);
}