package com.bailon.backend.users.application.usecase;

import com.bailon.backend.users.domain.model.User;
import com.bailon.backend.users.domain.model.UserRole;

import java.util.UUID;

public interface ChangeRoleUseCase {
    User execute(UUID userId, UserRole role);
}
