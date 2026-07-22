package com.bailon.backend.auth.domain.model;

import com.bailon.backend.users.domain.model.UserRole;

public record Auth(String accessToken, String refreshToken, UserRole role) {
}
