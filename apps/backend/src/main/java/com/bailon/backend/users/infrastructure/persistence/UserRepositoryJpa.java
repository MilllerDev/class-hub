package com.bailon.backend.users.infrastructure.persistence;

import com.bailon.backend.users.domain.model.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepositoryJpa extends JpaRepository<UserEntity, UUID> {
    Optional<UserEntity> findByEmail(String email);

    Optional<UserEntity> findByIdAndEnabledFalse(UUID id);

    Optional<UserEntity> findByEmailAndEnabledFalse(String email);

    boolean existsByEmail(String email);

    boolean existsByIdAndRole(UUID id, UserRole role);
}
