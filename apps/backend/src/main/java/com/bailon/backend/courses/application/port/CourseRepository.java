package com.bailon.backend.courses.application.port;

import com.bailon.backend.courses.domain.model.Course;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CourseRepository {
    Course save(Course course);

    Optional<Course> findById(UUID id);

    Optional<Course> findBySlug(String slug);

    Optional<Course> findEnabledBySlug(String slug);

    List<Course> findAllEnabled();

    List<Course> findAll();

    List<Course> findAllByOwner(UUID ownerId);

    List<Course> findLatest();

    long count();

    boolean existsByNameOrSlug(String name, String slug);

    boolean existsByNameOrSlug(String name, String slug, UUID excludedId);

    boolean existsById(UUID id);

    boolean teacherOwnsCourse(UUID courseId, UUID teacherId);

    void updateDescription(UUID id, String description);

    void delete(UUID id);
}