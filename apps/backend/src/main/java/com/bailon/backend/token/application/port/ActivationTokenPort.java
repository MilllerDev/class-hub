package com.bailon.backend.token.application.port;

import com.bailon.backend.token.domain.model.ActivationToken;

import java.util.Optional;

public interface ActivationTokenPort {
    void save(ActivationToken activationToken);

    boolean existsByToken(String token);

    Optional<ActivationToken> findByToken(String token);
}
