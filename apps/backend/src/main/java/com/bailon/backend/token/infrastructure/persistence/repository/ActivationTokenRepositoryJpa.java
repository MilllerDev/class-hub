package com.bailon.backend.token.infrastructure.persistence.repository;

import com.bailon.backend.token.infrastructure.persistence.entity.ActivationTokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

public interface ActivationTokenRepositoryJpa extends JpaRepository<ActivationTokenEntity, UUID> {
    Optional<ActivationTokenEntity> findByTokenAndUsedFalse(String token);

    @Modifying
    @Query("UPDATE ActivationTokenEntity a SET a.used = true WHERE a.user.id = :userId AND a.used = false")
    void revokeAllByUserId(UUID userId);

    @Modifying
    @Query("UPDATE ActivationTokenEntity a SET a.used = true, a.usedAt = :usedAt WHERE a.id = :id AND a.used = false")
    void markUsed(@Param("id") UUID id, @Param("usedAt") Instant usedAt);
}
