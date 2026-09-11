package com.bailon.backend.topicresources.application.port;

import com.bailon.backend.topicresources.domain.model.TopicResource;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TopicResourceRepository {
    TopicResource save(TopicResource resource);

    boolean existsById(UUID id);

    Optional<TopicResource> findByIdAndOwner(UUID id, UUID userId);

    List<TopicResource> findByTopic(UUID topicId);

    List<TopicResource> findResourcesOwner(UUID topicId, UUID userId);

    UUID findCourseByResourceId(UUID resourceId);

    boolean existsByOwnerId(UUID id, UUID userId);

    void delete(UUID id);
}
