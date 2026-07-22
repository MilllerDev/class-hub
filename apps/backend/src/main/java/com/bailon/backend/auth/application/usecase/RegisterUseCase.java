package com.bailon.backend.auth.application.usecase;

import com.bailon.backend.profile.domain.model.Profile;
import com.bailon.backend.users.domain.model.User;

public interface RegisterUseCase {
    User execute(Profile user);
}
