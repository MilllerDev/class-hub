package com.bailon.backend.token.infrastructure.persistence.adapter;

import com.bailon.backend.token.application.port.TokenRepositoryPort;
import com.bailon.backend.token.domain.model.Token;
import com.bailon.backend.token.infrastructure.hash.TokenHashUtil;
import com.bailon.backend.token.infrastructure.mapper.TokenMapper;
import com.bailon.backend.token.infrastructure.persistence.entity.TokenEntity;
import com.bailon.backend.token.infrastructure.persistence.repository.TokenRepositoryJpa;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

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
        TokenEntity entity = mapper.toEntity(token);
        entity.setTokenHash(TokenHashUtil.hash(token.getToken()));
        repositoryJpa.save(entity);
    }

    @Override
    public boolean existsValid(String token) {
        String hash = TokenHashUtil.hash(token);
        return repositoryJpa.existsByTokenHashAndRevokedFalse(hash);
    }
}
