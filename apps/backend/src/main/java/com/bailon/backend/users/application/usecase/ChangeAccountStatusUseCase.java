package com.bailon.backend.users.application.usecase;

import com.bailon.backend.users.domain.model.User;

import java.util.UUID;

public interface ChangeAccountStatusUseCase {
    User execute(UUID userId, boolean enabled);
}
