package com.bailon.backend.config.security.jwt;

import com.bailon.backend.users.domain.model.User;

public interface JwtPort {
    JwtData generateJwtAccess(User user);

    JwtData generateJwtRefresh(User user);

    String getEmailFromAccessToken(String token);

    String getEmailFromRefreshToken(String token);

    boolean validateAccessToken(String token, String email);

    boolean validateRefreshToken(String token, String email);
}
