package com.bailon.backend.topicresources.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TopicResourceRepositoryJpa extends JpaRepository<TopicResourceEntity, UUID> {
    List<TopicResourceEntity> findAllByTopicId(UUID topicId);

    List<TopicResourceEntity> findByTopicIdAndTopicCourseUserId(UUID topicId, UUID userId);

    Optional<TopicResourceEntity> findByIdAndTopicCourseUserId(UUID id, UUID userId);

    boolean existsByIdAndTopicCourseUserId(UUID id, UUID userId);

    @Query("SELECT t.topic.course.id FROM TopicResourceEntity t WHERE t.id = :resourceId")
    UUID findCourseByTopicResourceId(UUID resourceId);
}
