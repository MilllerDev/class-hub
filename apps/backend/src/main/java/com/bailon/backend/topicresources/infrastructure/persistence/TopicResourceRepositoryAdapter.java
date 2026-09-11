package com.bailon.backend.topicresources.infrastructure.persistence;

import com.bailon.backend.topicresources.application.port.TopicResourceRepository;
import com.bailon.backend.topicresources.domain.model.TopicResource;
import com.bailon.backend.topicresources.infrastructure.mapper.TopicResourceMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class TopicResourceRepositoryAdapter implements TopicResourceRepository {
    private final TopicResourceRepositoryJpa repositoryJpa;
    private final TopicResourceMapper mapper;

    public TopicResourceRepositoryAdapter(TopicResourceRepositoryJpa repositoryJpa, TopicResourceMapper mapper) {
        this.repositoryJpa = repositoryJpa;
        this.mapper = mapper;
    }

    @Override
    public TopicResource save(TopicResource resource) {
        TopicResourceEntity entity = mapper.toEntity(resource);
        return mapper.toDomain(repositoryJpa.save(entity));
    }

    @Override
    public boolean existsById(UUID id) {
        return repositoryJpa.existsById(id);
    }

    @Override
    public Optional<TopicResource> findByIdAndOwner(UUID id, UUID userId) {
        return repositoryJpa.findByIdAndTopicCourseUserId(id, userId).map(mapper::toDomain);
    }

    @Override
    public List<TopicResource> findByTopic(UUID topicId) {
        return repositoryJpa.findAllByTopicId(topicId).stream().map(mapper::toDomain).toList();
    }

    @Override
    public List<TopicResource> findResourcesOwner(UUID topicId, UUID userId) {
        return repositoryJpa.findByIdAndTopicCourseUserId(topicId, userId).stream().map(
                mapper::toDomain
        ).toList();
    }

    @Override
    public UUID findCourseByResourceId(UUID resourceId) {
        return repositoryJpa.findCourseByTopicResourceId(resourceId);
    }

    @Override
    public boolean existsByOwnerId(UUID id, UUID userId) {
        return repositoryJpa.existsByIdAndTopicCourseUserId(id, userId);
    }

    @Override
    public void delete(UUID id) {
        repositoryJpa.deleteById(id);
    }
}
