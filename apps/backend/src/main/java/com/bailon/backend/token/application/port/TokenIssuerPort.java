package com.bailon.backend.token.application.port;

import com.bailon.backend.token.domain.model.Auth;
import com.bailon.backend.users.domain.model.User;

import java.util.UUID;

public interface TokenIssuerPort {
    Auth issueToken(User user);

    String issueTokenActivation(UUID userId);
}
