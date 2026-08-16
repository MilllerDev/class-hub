package com.bailon.backend.profile.application.usecase;

import com.bailon.backend.profile.domain.model.Profile;
import com.bailon.backend.users.domain.model.UserRole;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface GetProfilesUseCase {
    Page<Profile> page(UUID userId, String query, UserRole role, Pageable pageable);

    List<Profile> list(UUID userId, UserRole role);
}