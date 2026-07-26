package com.bailon.backend.token.application.service;

import com.bailon.backend.auth.domain.model.Auth;
import com.bailon.backend.config.security.jwt.JwtData;
import com.bailon.backend.config.security.jwt.JwtPort;
import com.bailon.backend.token.application.port.ActivationTokenPort;
import com.bailon.backend.token.application.port.TokenRepositoryPort;
import com.bailon.backend.token.domain.model.ActivationToken;
import com.bailon.backend.token.domain.model.Token;
import com.bailon.backend.users.domain.model.User;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.UUID;

@Component
public class TokenIssuerService {
    private final JwtPort jwtPort;
    private final TokenRepositoryPort tokenPort;
    private final ActivationTokenPort activationTokenPort;

    public TokenIssuerService(JwtPort jwtPort, TokenRepositoryPort tokenPort, ActivationTokenPort activationTokenPort) {
        this.jwtPort = jwtPort;
        this.tokenPort = tokenPort;
        this.activationTokenPort = activationTokenPort;
    }

    @Transactional
    public Auth issueToken(User user) {
        tokenPort.revokeToken(user.getId());
        JwtData access = jwtPort.generateJwtAccess(user);
        JwtData refresh = jwtPort.generateJwtRefresh(user);
        tokenPort.save(Token.create(refresh.token(), user.getId(), refresh.expiresAt()));
        return new Auth(access.token(), refresh.token(), user.getRole());
    }

    @Transactional
    public String issueTokenActivation(UUID userId) {
        activationTokenPort.revokeAllByUserId(userId);
        ActivationToken activation = ActivationToken.create(userId);
        activation.setExpiresAt(Instant.now().plusSeconds(900));
        activationTokenPort.save(activation);
        return activation.getToken();
    }
}
