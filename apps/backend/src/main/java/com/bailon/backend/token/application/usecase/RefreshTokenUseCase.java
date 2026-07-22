package com.bailon.backend.token.application.usecase;

import com.bailon.backend.auth.domain.model.Auth;

public interface RefreshTokenUseCase {
    Auth execute(String token);
}
