package com.bailon.backend.courses.application.port;

import com.bailon.backend.courses.domain.model.Course;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CourseRepository {
    Course save(Course course);

    Optional<Course> findBySlug(String slug, boolean enabled);

    Optional<Course> findById(UUID id);

    List<Course> findAllActive();

    List<Course> findAll();

    List<Course> findAllOwnedBy(UUID userId);

    List<Course> findLatest();

    long count();

    boolean alreadyExists(String name, String slug);

    boolean alreadyExists(String name, String slug, UUID id);

    boolean existsBySlug(String slug);

    boolean existsEnabledBySlug(String slug);

    boolean teacherOwnsCourse(UUID courseId, UUID teacherId);

    boolean teacherOwnsCourse(String slug, UUID teacherId);

    boolean exists(UUID id);

    void delete(UUID id);
}
