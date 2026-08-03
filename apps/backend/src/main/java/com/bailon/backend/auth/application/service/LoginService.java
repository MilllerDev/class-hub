package com.bailon.backend.auth.application.service;

import com.bailon.backend.auth.application.usecase.LoginUseCase;
import com.bailon.backend.token.application.port.TokenIssuerPort;
import com.bailon.backend.token.domain.model.Auth;
import com.bailon.backend.users.application.port.UserRepository;
import com.bailon.backend.users.domain.exception.UserNotFoundException;
import com.bailon.backend.users.domain.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class LoginService implements LoginUseCase {
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final TokenIssuerPort tokenIssuer;

    @Override
    public Auth execute(String email, String password) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, password)
        );
        User user = userRepository.findByEmail(email).orElseThrow(
                () -> new UserNotFoundException("Usuario no encontrado")
        );
        user.setLastLogin(LocalDateTime.now());
        return tokenIssuer.issueToken(user);
    }
}
