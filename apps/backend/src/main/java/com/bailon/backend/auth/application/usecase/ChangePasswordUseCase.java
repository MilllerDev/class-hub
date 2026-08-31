package com.bailon.backend.auth.application.usecase;

import com.bailon.backend.token.domain.model.Auth;

import java.util.UUID;

public interface ChangePasswordUseCase {
    Auth execute(UUID id, String password, String newPassword, String confirmPassword);
}
