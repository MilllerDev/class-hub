package com.bailon.backend.profile.application.usecase;

import com.bailon.backend.profile.domain.model.Profile;

import java.util.List;
import java.util.UUID;

public interface GetProfilesUseCase {
    List<Profile> list(UUID userId);
}
