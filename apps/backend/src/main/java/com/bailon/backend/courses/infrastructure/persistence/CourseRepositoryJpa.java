package com.bailon.backend.courses.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CourseRepositoryJpa extends JpaRepository<CourseEntity, UUID> {
    Optional<CourseEntity> findBySlug(String slug);

    Optional<CourseEntity> findBySlugAndEnabled(String slug, boolean enabled);

    List<CourseEntity> findByEnabledTrue();

    List<CourseEntity> findByEnabledTrueAndUserId(UUID userId);

    boolean existsByNameAndSlug(String name, String slug);

    boolean existsBySlug(String slug);

    boolean existsByNameOrSlugAndIdNot(String name, String slug, UUID id);
}
