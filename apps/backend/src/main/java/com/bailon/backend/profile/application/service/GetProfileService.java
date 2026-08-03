package com.bailon.backend.profile.application.service;

import com.bailon.backend.profile.application.port.ProfileRepository;
import com.bailon.backend.profile.application.usecase.GetProfileUseCase;
import com.bailon.backend.profile.application.usecase.GetProfilesUseCase;
import com.bailon.backend.profile.domain.exception.ProfileNotFoundException;
import com.bailon.backend.profile.domain.model.Profile;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class GetProfileService implements GetProfileUseCase, GetProfilesUseCase {
    private final ProfileRepository repository;

    public GetProfileService(ProfileRepository repository) {
        this.repository = repository;
    }

    @Override
    public Profile execute(UUID userId) {
        return repository.findByUser(userId).orElseThrow(ProfileNotFoundException::new);
    }

    @Override
    public List<Profile> list(UUID userId) {
        return repository.findAll(userId);
    }
}
