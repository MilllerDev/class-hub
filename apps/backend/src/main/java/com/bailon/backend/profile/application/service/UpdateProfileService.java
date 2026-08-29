package com.bailon.backend.profile.application.service;

import com.bailon.backend.profile.application.port.ProfileRepository;
import com.bailon.backend.profile.application.usecase.UpdateProfileUseCase;
import com.bailon.backend.profile.domain.model.Profile;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UpdateProfileService implements UpdateProfileUseCase {
    private final ProfileRepository repository;

    public UpdateProfileService(ProfileRepository repository) {
        this.repository = repository;
    }

    @Override
    public Profile execute(UUID userId, Profile profile) {
        return repository.save(profile);
    }
}
