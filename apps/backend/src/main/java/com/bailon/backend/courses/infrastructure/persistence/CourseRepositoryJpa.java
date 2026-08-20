package com.bailon.backend.courses.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CourseRepositoryJpa extends JpaRepository<CourseEntity, UUID> {
    Optional<CourseEntity> findBySlug(String slug);

    Optional<CourseEntity> findBySlugAndEnabledTrue(String slug);

    List<CourseEntity> findByEnabledTrue();

    List<CourseEntity> findByUserId(UUID userId);

    List<CourseEntity> findTop5ByOrderByCreatedAtDesc();

    boolean existsByIdAndUserId(UUID id, UUID userId);

    boolean existsByNameOrSlug(String name, String slug);

    @Query("""
            SELECT CASE WHEN COUNT(c) > 0 THEN true ELSE false END FROM CourseEntity c
            WHERE (c.name = :name OR c.slug = :slug) AND c.id <> :id
            """)
    boolean existsByNameOrSlugAndIdNot(@Param("name") String name, @Param("slug") String slug, @Param("id") UUID id);

    @Modifying
    @Query("UPDATE CourseEntity c SET c.description = :description WHERE c.id = :id")
    void updateDescription(@Param("id") UUID id, @Param("description") String description);
}