package com.bailon.backend.token.infrastructure.persistence.repository;

import com.bailon.backend.token.infrastructure.persistence.entity.ActivationTokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface ActivationTokenRepositoryJpa extends JpaRepository<ActivationTokenEntity, UUID> {
    boolean existsByTokenAndUsedFalse(String token);

    Optional<ActivationTokenEntity> findByTokenAndUsedFalse(String token);

    @Modifying
    @Query("UPDATE ActivationTokenEntity a SET a.used = true WHERE a.user.id = :userId AND a.used = false")
    void revokeAllByUserId(UUID userId);
}
