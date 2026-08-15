package com.bailon.backend.courses.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CourseRepositoryJpa extends JpaRepository<CourseEntity, UUID> {
    Optional<CourseEntity> findBySlug(String slug);

    Optional<CourseEntity> findBySlugAndEnabled(String slug, boolean enabled);

    List<CourseEntity> findByEnabledTrue();

    List<CourseEntity> findByEnabledTrueAndUserId(UUID userId);

    List<CourseEntity> findTop5ByOrderByCreatedAtDesc();

    boolean existsByNameAndSlug(String name, String slug);

    boolean existsBySlug(String slug);

    boolean existsBySlugAndEnabled(String slug, boolean enabled);

    boolean existsBySlugAndUserId(String slug, UUID userId);

    boolean existsByIdAndUserId(UUID id, UUID userId);

    @Query("""
            SELECT CASE WHEN COUNT(c) > 0 THEN true ELSE false END FROM CourseEntity c
            WHERE (c.name = :name OR c.slug = :slug) AND c.id <> :id
            """)
    boolean existsByNameOrSlugAndIdNot(@Param("name") String name, @Param("slug") String slug, @Param("id") UUID id);
}
