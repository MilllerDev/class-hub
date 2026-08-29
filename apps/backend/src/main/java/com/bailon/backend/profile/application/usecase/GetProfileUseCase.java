package com.bailon.backend.profile.application.usecase;

import com.bailon.backend.profile.domain.model.Profile;

import java.util.UUID;

public interface GetProfileUseCase {
    Profile me(UUID userId);

    Profile other(UUID userId);
}
