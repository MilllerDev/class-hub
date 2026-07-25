package com.bailon.backend.auth.application.usecase;

import com.bailon.backend.auth.domain.model.Auth;

public interface ActivationAccountUseCase {
    Auth execute(String token);
}
