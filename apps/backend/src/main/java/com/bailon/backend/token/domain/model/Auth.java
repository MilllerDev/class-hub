package com.bailon.backend.token.domain.model;

import com.bailon.backend.users.domain.model.UserRole;

public record Auth(String accessToken, String refreshToken, UserRole role) {
}
