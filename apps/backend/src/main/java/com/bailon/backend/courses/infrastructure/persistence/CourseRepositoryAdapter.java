package com.bailon.backend.courses.infrastructure.persistence;

import com.bailon.backend.courses.application.port.CourseRepository;
import com.bailon.backend.courses.domain.model.Course;
import com.bailon.backend.courses.infrastructure.mapper.CourseMapper;
import com.bailon.backend.users.infrastructure.persistence.UserRepositoryJpa;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class CourseRepositoryAdapter implements CourseRepository {
    private final CourseRepositoryJpa repositoryJpa;
    private final UserRepositoryJpa userRepositoryJpa;
    private final CourseMapper mapper;

    public CourseRepositoryAdapter(CourseRepositoryJpa repositoryJpa, UserRepositoryJpa userRepositoryJpa, CourseMapper mapper) {
        this.repositoryJpa = repositoryJpa;
        this.userRepositoryJpa = userRepositoryJpa;
        this.mapper = mapper;
    }

    @Override
    public Course save(Course course) {
        CourseEntity entity = mapper.toEntity(course);
        if (course.getUserId() != null) {
            entity.setUser(userRepositoryJpa.getReferenceById(course.getUserId()));
        }
        return mapper.toDomain(repositoryJpa.save(entity));
    }

    @Override
    public Optional<Course> findById(UUID id) {
        return repositoryJpa.findById(id).map(mapper::toDomain);
    }

    @Override
    public Optional<Course> findBySlug(String slug) {
        return repositoryJpa.findBySlug(slug).map(mapper::toDomain);
    }

    @Override
    public Optional<Course> findEnabledBySlug(String slug) {
        return repositoryJpa.findBySlugAndEnabledTrue(slug).map(mapper::toDomain);
    }

    @Override
    public List<Course> findAllEnabled() {
        return repositoryJpa.findByEnabledTrue().stream().map(mapper::toDomain).toList();
    }

    @Override
    public List<Course> findAll() {
        return repositoryJpa.findAll().stream().map(mapper::toDomain).toList();
    }

    @Override
    public List<Course> findAllByOwner(UUID ownerId) {
        return repositoryJpa.findByUserId(ownerId).stream().map(mapper::toDomain).toList();
    }

    @Override
    public List<Course> findLatest() {
        return repositoryJpa.findTop5ByOrderByCreatedAtDesc().stream().map(mapper::toDomain).toList();
    }

    @Override
    public long count() {
        return repositoryJpa.count();
    }

    @Override
    public boolean existsByNameOrSlug(String name, String slug) {
        return repositoryJpa.existsByNameOrSlug(name, slug);
    }

    @Override
    public boolean existsByNameOrSlug(String name, String slug, UUID excludedId) {
        return repositoryJpa.existsByNameOrSlugAndIdNot(name, slug, excludedId);
    }

    @Override
    public boolean existsById(UUID id) {
        return repositoryJpa.existsById(id);
    }

    @Override
    public boolean teacherOwnsCourse(UUID courseId, UUID teacherId) {
        return repositoryJpa.existsByIdAndUserId(courseId, teacherId);
    }

    @Override
    @Transactional
    public void updateDescription(UUID id, String description) {
        repositoryJpa.updateDescription(id, description);
    }

    @Override
    public void delete(UUID id) {
        repositoryJpa.deleteById(id);
    }
}