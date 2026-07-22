package com.bailon.backend.token.application.port;

import com.bailon.backend.token.domain.model.Token;

import java.util.Optional;
import java.util.UUID;

public interface TokenRepositoryPort {
    void revokeToken(UUID userId);

    void save(Token token);

    Optional<Token> findByToken(String token);

    boolean existsValid(String token);
}
