package com.bailon.backend.profile.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProfileRepositoryJpa extends JpaRepository<ProfileEntity, UUID> {
    Optional<ProfileEntity> findByUserId(UUID userId);

    List<ProfileEntity> findByUserIdNot(UUID userId);

    List<ProfileEntity> findTop5ByOrderByUserCreatedAtDesc();
}
