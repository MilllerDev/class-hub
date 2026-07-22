package com.bailon.backend.auth.application.service;

import com.bailon.backend.auth.domain.model.Auth;
import com.bailon.backend.config.security.jwt.JwtData;
import com.bailon.backend.config.security.jwt.JwtPort;
import com.bailon.backend.token.application.port.TokenRepositoryPort;
import com.bailon.backend.token.domain.model.Token;
import com.bailon.backend.users.domain.model.User;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class AuthTokenIssuer {
    private final JwtPort jwtPort;
    private final TokenRepositoryPort tokenRepositoryPort;

    public AuthTokenIssuer(JwtPort jwtPort, TokenRepositoryPort tokenRepositoryPort) {
        this.jwtPort = jwtPort;
        this.tokenRepositoryPort = tokenRepositoryPort;
    }

    public Auth issueToken(User user) {
        tokenRepositoryPort.revokeToken(user.getId());
        JwtData access = jwtPort.generateJwtAccess(user);
        JwtData refresh = jwtPort.generateJwtRefresh(user);
        tokenRepositoryPort.save(Token.create(refresh.token(), user.getId(), refresh.expiresAt()));
        return new Auth(access.token(), refresh.token(), user.getRole());

    }
}
