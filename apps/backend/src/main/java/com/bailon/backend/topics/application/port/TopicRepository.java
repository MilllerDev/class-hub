package com.bailon.backend.topics.application.port;

import com.bailon.backend.topics.domain.model.Topic;
import com.bailon.backend.topics.domain.model.TopicOrder;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TopicRepository {
    Topic save(Topic topic);

    Optional<Topic> findById(UUID id);

    Optional<Topic> findBelongTeacher(UUID id, UUID userId);

    List<Topic> findByCourseSlug(String slug, TopicOrder order);

    List<Topic> findAllBelongTeacher(String slug, UUID userId, TopicOrder order);

    UUID findCourse(UUID topicId);

    boolean exists(UUID id);

    boolean courseOwnedByTeacher(UUID courseId, UUID teacherId);

    boolean existsTopicOwnsTeacher(UUID id, UUID userId);

    void delete(UUID id);
}