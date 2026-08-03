package com.bailon.backend.profile.application.usecase;

import com.bailon.backend.profile.domain.model.Profile;

import java.util.UUID;

public interface GetProfileUseCase {
    Profile execute(UUID userId);
}
