package com.bailon.backend.auth.application.service;

import com.bailon.backend.auth.application.usecase.LoginUseCase;
import com.bailon.backend.auth.domain.model.Auth;
import com.bailon.backend.token.application.service.TokenIssuerService;
import com.bailon.backend.users.domain.model.User;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class LoginService implements LoginUseCase {
    private final AuthenticationManager authenticationManager;
    private final TokenIssuerService tokenIssuer;

    public LoginService(AuthenticationManager authenticationManager, TokenIssuerService tokenIssuer) {
        this.authenticationManager = authenticationManager;
        this.tokenIssuer = tokenIssuer;
    }

    @Override
    public Auth execute(String email, String password) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, password)
        );
        User user = (User) authentication.getPrincipal();
        return tokenIssuer.issueToken(user);
    }
}
