package com.bailon.backend.topics.infrastructure.persistence;

import com.bailon.backend.topics.application.port.TopicRepository;
import com.bailon.backend.topics.domain.model.Topic;
import com.bailon.backend.topics.domain.model.TopicOrder;
import com.bailon.backend.topics.infrastructure.mapper.TopicMapper;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class TopicRepositoryAdapter implements TopicRepository {
    private final TopicRepositoryJpa repositoryJpa;
    private final TopicMapper mapper;

    public TopicRepositoryAdapter(TopicRepositoryJpa repositoryJpa, TopicMapper mapper) {
        this.repositoryJpa = repositoryJpa;
        this.mapper = mapper;
    }

    @Override
    public Topic save(Topic topic) {
        TopicEntity entity = mapper.toEntity(topic);
        return mapper.toDomain(repositoryJpa.save(entity));
    }

    @Override
    public Optional<Topic> findById(UUID id) {
        return repositoryJpa.findById(id).map(mapper::toDomain);
    }

    @Override
    public Optional<Topic> findBelongTeacher(UUID id, UUID userId) {
        return repositoryJpa.findByIdAndCourseUserId(id, userId).map(mapper::toDomain);
    }

    @Override
    public List<Topic> findByCourseSlug(String slug, TopicOrder order) {
        return repositoryJpa.findByCourseSlug(slug, toSort(order)).stream().map(mapper::toDomain).toList();
    }

    @Override
    public List<Topic> findAllBelongTeacher(String slug, UUID userId, TopicOrder order) {
        return repositoryJpa.findByCourseSlugAndCourseUserId(slug, userId, toSort(order)).stream().map(
                mapper::toDomain
        ).toList();
    }

    @Override
    public UUID findCourse(UUID topicId) {
        return repositoryJpa.findCourseByTopicId(topicId);
    }

    @Override
    public boolean exists(UUID id) {
        return repositoryJpa.existsById(id);
    }

    @Override
    public boolean courseOwnedByTeacher(UUID courseId, UUID teacherId) {
        return repositoryJpa.existsByCourseIdAndCourseUserId(courseId, teacherId);
    }

    @Override
    public boolean existsTopicOwnsTeacher(UUID id, UUID userId) {
        return repositoryJpa.existsByIdAndCourseUserId(id, userId);
    }

    @Override
    public void delete(UUID id) {
        repositoryJpa.deleteById(id);
    }

    private Sort toSort(TopicOrder order) {
        return Sort.by(
                order == TopicOrder.NEWEST ? Sort.Direction.DESC : Sort.Direction.ASC,
                "createdAt"
        );
    }
}