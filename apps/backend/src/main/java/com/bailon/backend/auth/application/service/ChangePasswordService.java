package com.bailon.backend.auth.application.service;

import com.bailon.backend.auth.application.usecase.ChangePasswordUseCase;
import com.bailon.backend.auth.domain.exception.CurrentPasswordMismatchException;
import com.bailon.backend.auth.domain.exception.PasswordMismatchException;
import com.bailon.backend.token.application.port.TokenIssuerPort;
import com.bailon.backend.token.domain.model.Auth;
import com.bailon.backend.users.application.port.UserRepository;
import com.bailon.backend.users.domain.exception.UserNotFoundException;
import com.bailon.backend.users.domain.model.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class ChangePasswordService implements ChangePasswordUseCase {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenIssuerPort tokenIssuer;

    public ChangePasswordService(UserRepository userRepository, PasswordEncoder passwordEncoder, TokenIssuerPort tokenIssuer) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenIssuer = tokenIssuer;
    }

    @Override
    @Transactional
    public Auth execute(UUID id, String password, String newPassword, String confirmPassword) {
        User found = userRepository.findActive(id).orElseThrow(
                () -> new UserNotFoundException("No se encontró al usuario")
        );
        if (!passwordEncoder.matches(password, found.getPassword())) {
            throw new CurrentPasswordMismatchException();
        }
        if (!found.validatePassword(newPassword, confirmPassword)) {
            throw new PasswordMismatchException();
        }
        found.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(found);
        return tokenIssuer.issueToken(found);
    }
}
