package com.bailon.backend.auth.application.service;

import com.bailon.backend.auth.domain.model.Auth;
import com.bailon.backend.config.security.jwt.JwtPort;
import com.bailon.backend.auth.domain.exception.InvalidActivationTokenException;
import com.bailon.backend.users.application.port.UserRepository;
import com.bailon.backend.users.domain.exception.UserNotFoundException;
import com.bailon.backend.users.domain.model.User;
import org.springframework.stereotype.Service;

@Service
public class ActivateAccountService {
    private final JwtPort jwtPort;
    private final UserRepository userRepository;

    public ActivateAccountService(JwtPort jwtPort, UserRepository userRepository) {
        this.jwtPort = jwtPort;
        this.userRepository = userRepository;
    }

    public void execute(String token) {
        if (!jwtPort.validateActivationToken(token)) {
            throw new InvalidActivationTokenException("Invalid or expired activation token");
        }
        String email = jwtPort.getEmailFromActivationToken(token);
        User user = userRepository.findByEmail(email).orElseThrow(
                () -> new UserNotFoundException("User not found")
        );
        user.setEnabled(true);
        userRepository.save(user);

    }
}
