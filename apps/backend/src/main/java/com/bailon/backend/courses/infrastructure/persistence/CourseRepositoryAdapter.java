package com.bailon.backend.courses.infrastructure.persistence;

import com.bailon.backend.courses.application.port.CourseRepository;
import com.bailon.backend.courses.domain.model.Course;
import com.bailon.backend.courses.infrastructure.mapper.CourseMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class CourseRepositoryAdapter implements CourseRepository {
    private final CourseRepositoryJpa repositoryJpa;
    private final CourseMapper mapper;

    public CourseRepositoryAdapter(CourseRepositoryJpa repositoryJpa, CourseMapper mapper) {
        this.repositoryJpa = repositoryJpa;
        this.mapper = mapper;
    }

    @Override
    public Course save(Course course) {
        CourseEntity entity = mapper.toEntity(course);
        return mapper.toDomain(repositoryJpa.save(entity));
    }

    @Override
    public Optional<Course> findBySlug(String slug, boolean enabled) {
        return repositoryJpa.findBySlugAndEnabled(slug, enabled).map(mapper::toDomain);
    }

    @Override
    public Optional<Course> findById(UUID id) {
        return repositoryJpa.findById(id).map(mapper::toDomain);
    }

    @Override
    public List<Course> findAllActive() {
        return repositoryJpa.findByEnabledTrue().stream().map(mapper::toDomain).toList();
    }

    @Override
    public List<Course> findAll() {
        return repositoryJpa.findAll().stream().map(mapper::toDomain).toList();
    }

    @Override
    public List<Course> findAllOwnedBy(UUID userId) {
        return repositoryJpa.findByEnabledTrueAndUserId(userId).stream().map(mapper::toDomain).toList();
    }

    @Override
    public boolean alreadyExists(String name, String slug) {
        return repositoryJpa.existsByNameAndSlug(name, slug);
    }

    @Override
    public boolean alreadyExists(String name, String slug, UUID id) {
        return repositoryJpa.existsByNameOrSlugAndIdNot(name, slug, id);
    }

    @Override
    public boolean existsBySlug(String slug) {
        return repositoryJpa.existsBySlug(slug);
    }

    @Override
    public boolean exists(UUID id) {
        return repositoryJpa.existsById(id);
    }

    @Override
    public void delete(UUID id) {
        repositoryJpa.deleteById(id);
    }
}
