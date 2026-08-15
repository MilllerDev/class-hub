package com.bailon.backend.profile.application.port;

import com.bailon.backend.profile.domain.model.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProfileRepository {
    Profile save(Profile profile);

    Optional<Profile> findByUser(UUID userId);

    List<Profile> findByUserIds(Collection<UUID> userIds);

    Page<Profile> search(UUID userId, String query, Pageable pageable);

    List<Profile> findLatest();
}
