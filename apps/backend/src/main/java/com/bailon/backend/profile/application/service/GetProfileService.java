package com.bailon.backend.profile.application.service;

import com.bailon.backend.profile.application.port.ProfileRepository;
import com.bailon.backend.profile.application.usecase.GetProfileUseCase;
import com.bailon.backend.profile.application.usecase.GetProfilesUseCase;
import com.bailon.backend.profile.domain.exception.ProfileNotFoundException;
import com.bailon.backend.profile.domain.model.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

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
    public Page<Profile> list(UUID userId, String query, Pageable pageable) {
        String normalized = query == null ? "" : query.trim().toLowerCase();
        if (normalized.isEmpty()) {
            return Page.empty(pageable);
        }
        return repository.search(userId, normalized, pageable);
    }
}
