package com.bailon.backend.token.infrastructure.persistence.adapter;

import com.bailon.backend.token.application.port.ActivationTokenPort;
import com.bailon.backend.token.domain.model.ActivationToken;
import com.bailon.backend.token.infrastructure.mapper.TokenMapper;
import com.bailon.backend.token.infrastructure.persistence.repository.ActivationTokenRepositoryJpa;
import org.springframework.stereotype.Repository;

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
        repositoryJpa.save(mapper.toEntity(activationToken));
    }

    @Override
    public boolean existsByToken(String token) {
        return repositoryJpa.existsByTokenAndUsedFalse(token);
    }

    @Override
    public Optional<ActivationToken> findByToken(String token) {
        return repositoryJpa.findByTokenAndUsedFalse(token).map(mapper::toDomain);
    }

    @Override
    public void revokeAllByUserId(UUID userId) {
        repositoryJpa.revokeAllByUserId(userId);
    }
}
