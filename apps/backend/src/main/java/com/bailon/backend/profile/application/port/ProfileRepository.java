package com.bailon.backend.profile.application.port;

import com.bailon.backend.profile.domain.model.Profile;
import com.bailon.backend.users.domain.model.UserRole;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProfileRepository {
    Profile save(Profile profile);

    Optional<Profile> findByUser(UUID userId);

    Optional<Profile> findActiveUser(UUID userId);

    List<Profile> findByUserIds(Collection<UUID> userIds);

    List<Profile> profilesByRole(UserRole role, UUID userId);

    Page<Profile> search(UUID userId, String query, UserRole role, Pageable pageable);

    List<Profile> findLatest();
}
