package com.bailon.backend.auth.application.service;

import com.bailon.backend.auth.application.usecase.ActivationAccountUseCase;
import com.bailon.backend.auth.domain.exception.InvalidActivationTokenException;
import com.bailon.backend.token.application.port.ActivationTokenPort;
import com.bailon.backend.token.application.port.TokenIssuerPort;
import com.bailon.backend.token.domain.model.ActivationToken;
import com.bailon.backend.token.domain.model.Auth;
import com.bailon.backend.users.application.port.UserRepository;
import com.bailon.backend.users.domain.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class ActivateAccountService implements ActivationAccountUseCase {
    private final TokenIssuerPort tokenIssuer;
    private final ActivationTokenPort activationTokenPort;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public Auth execute(String token) {
        ActivationToken activation = activationTokenPort.findByToken(token).orElseThrow(
                () -> new InvalidActivationTokenException("Token de activación inválido")
        );
        if (activation.isExpired(Instant.now())) {
            throw new InvalidActivationTokenException("Token de activación expirado");
        }
        activation.markUsed();
        User user = userRepository.findByIdPendingActivation(activation.getUserId()).orElseThrow(
                () -> new InvalidActivationTokenException("Usuario no encontrado o inactivo")
        );
        user.activate();
        user.recordLogin();
        userRepository.save(user);
        activationTokenPort.markUsed(activation.getId(), activation.getUsedAt());
        return tokenIssuer.issueToken(user);
    }
}
