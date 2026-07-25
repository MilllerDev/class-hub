package com.bailon.backend.token.infrastructure;

import com.bailon.backend.token.infrastructure.entity.ActivationTokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ActivationTokenRepositoryJpa extends JpaRepository<ActivationTokenEntity, UUID> {
    boolean existsByTokenAndUsedFalse(String token);

    Optional<ActivationTokenEntity> findByTokenAndUsedFalse(String token);
}
