package com.bailon.backend.topicresources.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TopicResourceRepositoryJpa extends JpaRepository<TopicResourceEntity, UUID> {
    List<TopicResourceEntity> findAllByTopicId(UUID topicId);

    Optional<TopicResourceEntity> findByIdAndTopicId(UUID id, UUID topicId);

    List<TopicResourceEntity> findAllByTopicCourseId(UUID courseId);
}
