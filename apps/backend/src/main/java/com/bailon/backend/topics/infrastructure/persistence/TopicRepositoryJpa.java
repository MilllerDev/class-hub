package com.bailon.backend.topics.infrastructure.persistence;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TopicRepositoryJpa extends JpaRepository<TopicEntity, UUID> {
    List<TopicEntity> findByCourseSlug(String slug, Sort sort);

    List<TopicEntity> findByCourseSlugAndCourseUserId(String slug, UUID userId, Sort sort);

    boolean existsByCourseIdAndCourseUserId(UUID id, UUID userId);

    boolean existsByIdAndCourseUserId(UUID id, UUID userId);

    Optional<TopicEntity> findByIdAndCourseUserId(UUID id, UUID userId);

    @Query("SELECT t.course.id FROM TopicEntity t WHERE t.id= :topicId")
    UUID findCourseByTopicId(UUID topicId);
}