package com.bailon.backend.token.application.service;

import com.bailon.backend.auth.domain.model.Auth;
import com.bailon.backend.config.security.jwt.JwtPort;
import com.bailon.backend.token.application.port.TokenRepositoryPort;
import com.bailon.backend.token.application.usecase.RefreshTokenUseCase;
import com.bailon.backend.token.domain.exception.InvalidRefreshTokenException;
import com.bailon.backend.token.domain.exception.RefreshTokenNotFoundException;
import com.bailon.backend.users.application.port.UserRepository;
import com.bailon.backend.users.domain.exception.UserNotFoundException;
import com.bailon.backend.users.domain.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RefreshTokenService implements RefreshTokenUseCase {
    private final JwtPort jwtPort;
    private final UserRepository userRepository;
    private final TokenRepositoryPort tokenRepositoryPort;
    private final TokenIssuerService tokenIssuer;

    @Override
    public Auth execute(String token) {
        if (!tokenRepositoryPort.existsValid(token)) {
            throw new RefreshTokenNotFoundException("No se encontró el token o está revocado");
        }
        String email = jwtPort.getEmailFromRefreshToken(token);
        if (!jwtPort.validateRefreshToken(token, email)) {
            throw new InvalidRefreshTokenException("Token inválido o expirado");
        }
        User user = userRepository.findByEmail(email).orElseThrow(
                () -> new UserNotFoundException("Usuario no encontrado")
        );
        return tokenIssuer.issueToken(user);
    }
}
