package com.bailon.backend.profile.application.port;

import com.bailon.backend.profile.domain.model.Profile;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProfileRepository {
    Profile save(Profile profile);

    Optional<Profile> findByUser(UUID userId);

    List<Profile> findAll(UUID userId);

    List<Profile> findLatest();
}
