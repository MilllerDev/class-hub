package com.bailon.backend.token.infrastructure;

import com.bailon.backend.token.application.port.TokenRepositoryPort;
import com.bailon.backend.token.domain.model.Token;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class TokenRepositoryAdapter implements TokenRepositoryPort {
    private final TokenRepositoryJpa repositoryJpa;
    private final TokenMapper mapper;

    @Override
    public void revokeToken(UUID userId) {
        repositoryJpa.revokeAllByUserId(userId);
    }

    @Override
    public void save(Token token) {
        repositoryJpa.save(mapper.toEntity(token));
    }

    @Override
    public Optional<Token> findByToken(String token) {
        return repositoryJpa.findByToken(token).map(mapper::toDomain);
    }

    @Override
    public boolean existsValid(String token) {
        return repositoryJpa.existsByTokenAndRevokedFalse(token);
    }
}
