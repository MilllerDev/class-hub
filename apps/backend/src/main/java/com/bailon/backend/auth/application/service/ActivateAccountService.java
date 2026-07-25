package com.bailon.backend.auth.application.service;

import com.bailon.backend.auth.application.usecase.ActivationAccountUseCase;
import com.bailon.backend.auth.domain.exception.InvalidActivationTokenException;
import com.bailon.backend.auth.domain.model.Auth;
import com.bailon.backend.token.application.port.ActivationTokenPort;
import com.bailon.backend.token.application.service.TokenIssuerService;
import com.bailon.backend.token.domain.model.ActivationToken;
import com.bailon.backend.users.application.port.UserRepository;
import com.bailon.backend.users.domain.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class ActivateAccountService implements ActivationAccountUseCase {
    private final TokenIssuerService tokenIssuer;
    private final ActivationTokenPort activationTokenPort;
    private final UserRepository userRepository;

    @Override
    public Auth execute(String token) {
        ActivationToken activation = activationTokenPort.findByToken(token).orElseThrow(
                () -> new InvalidActivationTokenException("Token de activación inválido")
        );
        if (activation.getExpiresAt().isBefore(Instant.now())) {
            throw new InvalidActivationTokenException("Token de activación expirado");
        }
        activation.setUsed(true);
        activation.setUsedAt(Instant.now());
        User user = userRepository.findByIdInactive(activation.getUserId()).orElseThrow(
                () -> new InvalidActivationTokenException("Usuario no encontrado o inactivo")
        );
        user.setEnabled(true);
        userRepository.save(user);
        activationTokenPort.save(activation);
        return tokenIssuer.issueToken(user);
    }
}
