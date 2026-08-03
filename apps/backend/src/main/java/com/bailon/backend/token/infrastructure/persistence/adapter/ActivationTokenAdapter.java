package com.bailon.backend.token.infrastructure.persistence.adapter;

import com.bailon.backend.token.application.port.ActivationTokenPort;
import com.bailon.backend.token.domain.model.ActivationToken;
import com.bailon.backend.token.infrastructure.hash.TokenHashUtil;
import com.bailon.backend.token.infrastructure.mapper.TokenMapper;
import com.bailon.backend.token.infrastructure.persistence.entity.ActivationTokenEntity;
import com.bailon.backend.token.infrastructure.persistence.repository.ActivationTokenRepositoryJpa;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Repository
public class ActivationTokenAdapter implements ActivationTokenPort {
    private final ActivationTokenRepositoryJpa repositoryJpa;
    private final TokenMapper mapper;

    public ActivationTokenAdapter(ActivationTokenRepositoryJpa repositoryJpa, TokenMapper mapper) {
        this.repositoryJpa = repositoryJpa;
        this.mapper = mapper;
    }

    @Override
    public void save(ActivationToken activationToken) {
        ActivationTokenEntity entity = mapper.toEntity(activationToken);
        entity.setToken(TokenHashUtil.hash(activationToken.getToken()));
        repositoryJpa.save(entity);
    }

    @Override
    public Optional<ActivationToken> findByToken(String token) {
        return repositoryJpa.findByTokenAndUsedFalse(TokenHashUtil.hash(token)).map(mapper::toDomain);
    }

    @Override
    public void revokeAllByUserId(UUID userId) {
        repositoryJpa.revokeAllByUserId(userId);
    }

    @Override
    public void markUsed(UUID id, Instant usedAt) {
        repositoryJpa.markUsed(id, usedAt);
    }
}
