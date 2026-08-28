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
    public Optional<TopicResource> findById(UUID id) {
        return repositoryJpa.findById(id).map(mapper::toDomain);
    }

    @Override
    public Optional<TopicResource> findByIdAndTopicId(UUID id, UUID topicId) {
        return repositoryJpa.findByIdAndTopicId(id, topicId).map(mapper::toDomain);
    }

    @Override
    public List<TopicResource> findByTopicId(UUID topicId) {
        return repositoryJpa.findAllByTopicId(topicId).stream().map(mapper::toDomain).toList();
    }

    @Override
    public List<UUID> findIdsByTopicId(UUID topicId) {
        return repositoryJpa.findAllByTopicId(topicId).stream().map(TopicResourceEntity::getId).toList();
    }

    @Override
    public List<UUID> findIdsByCourseId(UUID courseId) {
        return repositoryJpa.findAllByTopicCourseId(courseId).stream().map(TopicResourceEntity::getId).toList();
    }

    @Override
    public void delete(UUID id) {
        repositoryJpa.deleteById(id);
    }
}
