package com.bailon.backend.topicresources.application.port;

import com.bailon.backend.topicresources.domain.model.TopicResource;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TopicResourceRepository {
    TopicResource save(TopicResource resource);

    Optional<TopicResource> findById(UUID id);

    Optional<TopicResource> findByIdAndTopicId(UUID id, UUID topicId);

    List<TopicResource> findByTopicId(UUID topicId);

    List<UUID> findIdsByTopicId(UUID topicId);

    List<UUID> findIdsByCourseId(UUID courseId);

    void delete(UUID id);
}
