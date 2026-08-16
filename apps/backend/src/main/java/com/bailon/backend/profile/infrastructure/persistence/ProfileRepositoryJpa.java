package com.bailon.backend.profile.infrastructure.persistence;

import com.bailon.backend.users.domain.model.UserRole;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProfileRepositoryJpa extends JpaRepository<ProfileEntity, UUID> {
    Optional<ProfileEntity> findByUserId(UUID userId);

    List<ProfileEntity> findByUserIdIn(Collection<UUID> userIds);

    List<ProfileEntity> findTop5ByOrderByUserCreatedAtDesc();

    List<ProfileEntity> findByUserRoleAndUserIdNot(UserRole userRole, UUID userId);

    List<ProfileEntity> findByUserIdNot(UUID userId);

    @Query("""
            SELECT p FROM ProfileEntity p
            WHERE p.user.id <> :userId
              AND (:role IS NULL OR p.user.role = :role)
              AND (:query IS NULL OR :query = ''
                   OR p.name ILIKE CONCAT('%', :query, '%')
                   OR p.lastName ILIKE CONCAT('%', :query, '%')
                   OR p.user.email ILIKE CONCAT('%', :query, '%')
                   OR CAST(p.user.role AS string) ILIKE CONCAT('%', :query, '%'))
            ORDER BY p.user.createdAt DESC
            """)
    Page<ProfileEntity> search(
            @Param("userId") UUID userId,
            @Param("query") String query,
            @Param("role") UserRole role,
            Pageable pageable
    );
}
