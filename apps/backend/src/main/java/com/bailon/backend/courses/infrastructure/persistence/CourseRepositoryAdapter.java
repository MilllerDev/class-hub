package com.bailon.backend.courses.infrastructure.persistence;

import com.bailon.backend.courses.application.port.CourseRepository;
import com.bailon.backend.courses.domain.model.Course;
import com.bailon.backend.courses.infrastructure.mapper.CourseMapper;
import com.bailon.backend.users.infrastructure.persistence.UserRepositoryJpa;
import org.springframework.stereotype.Repository;

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
    public List<Course> findLatest() {
        return repositoryJpa.findTop5ByOrderByCreatedAtDesc().stream().map(mapper::toDomain).toList();
    }

    @Override
    public long count() {
        return repositoryJpa.count();
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
    public boolean existsEnabledBySlug(String slug) {
        return repositoryJpa.existsBySlugAndEnabled(slug, true);
    }

    @Override
    public boolean teacherOwnsCourse(UUID courseId, UUID teacherId) {
        return repositoryJpa.existsByIdAndUserId(courseId, teacherId);
    }

    @Override
    public boolean teacherOwnsCourse(String slug, UUID teacherId) {
        return repositoryJpa.existsBySlugAndUserId(slug, teacherId);
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
