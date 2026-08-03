package com.bailon.backend.token.application.port;

import com.bailon.backend.token.domain.model.JwtData;
import com.bailon.backend.users.domain.model.User;

import java.util.Optional;

public interface JwtPort {
    JwtData generateJwtAccess(User user);

    JwtData generateJwtRefresh(User user);

    Optional<String> subjectFromValidAccessToken(String token);

    Optional<String> subjectFromValidRefreshToken(String token);
}
