package com.bailon.backend.token.infrastructure;

import com.bailon.backend.token.infrastructure.entity.TokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface TokenRepositoryJpa extends JpaRepository<TokenEntity, UUID> {
    Optional<TokenEntity> findByToken(String token);

    boolean existsByTokenAndRevokedFalse(String token);

    @Modifying
    @Query("UPDATE TokenEntity t SET t.revoked = true WHERE t.user.id = :userId AND t.revoked = false")
    void revokeAllByUserId(UUID userId);
}
