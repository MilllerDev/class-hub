package com.bailon.backend.profile.application.usecase;

import com.bailon.backend.profile.domain.model.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface GetProfilesUseCase {
    Page<Profile> list(UUID userId, String query, Pageable pageable);
}