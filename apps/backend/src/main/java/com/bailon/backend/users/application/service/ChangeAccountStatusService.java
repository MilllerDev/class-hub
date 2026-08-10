package com.bailon.backend.users.application.service;

import com.bailon.backend.token.application.port.TokenRepositoryPort;
import com.bailon.backend.users.application.port.UserRepository;
import com.bailon.backend.users.application.usecase.ChangeAccountStatusUseCase;
import com.bailon.backend.users.domain.exception.UserNotFoundException;
import com.bailon.backend.users.domain.model.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class ChangeAccountStatusService implements ChangeAccountStatusUseCase {
    private final UserRepository userRepository;
    private final TokenRepositoryPort tokenRepositoryPort;

    public ChangeAccountStatusService(UserRepository userRepository, TokenRepositoryPort tokenRepositoryPort) {
        this.userRepository = userRepository;
        this.tokenRepositoryPort = tokenRepositoryPort;
    }

    @Override
    @Transactional
    public User execute(UUID userId, boolean enabled) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new UserNotFoundException("No se encontró al usuario")
        );
        if (enabled) {
            user.activate();
        } else {
            user.deactivate();
            tokenRepositoryPort.revokeToken(user.getId());
        }
        return userRepository.save(user);
    }
}
