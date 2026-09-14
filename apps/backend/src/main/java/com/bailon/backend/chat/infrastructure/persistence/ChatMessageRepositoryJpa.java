package com.bailon.backend.chat.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface ChatMessageRepositoryJpa extends JpaRepository<ChatMessageEntity, UUID> {
    List<ChatMessageEntity> findByTopicIdAndUserIdOrderByCreatedAtAsc(UUID topicId, UUID userId);

    boolean existsByIdAndTopicIdAndUserId(UUID id, UUID topicId, UUID userId);

    @Query("SELECT c FROM ChatMessageEntity c WHERE  c.user.id =:userId ORDER BY c.createdAt ASC LIMIT 4")
    List<ChatMessageEntity> lastChats(UUID userId);
}