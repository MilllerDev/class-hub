package com.bailon.backend.profile.infrastructure.persistence;

import com.bailon.backend.profile.application.port.ProfileRepository;
import com.bailon.backend.profile.domain.model.Profile;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class ProfileRepositoryAdapter implements ProfileRepository {

    private final JpaProfileRepository jpaProfileRepository;
    private final ProfileMapper profileMapper;

    @Override
    public Profile save(Profile profile) {
        return profileMapper.toDomain(jpaProfileRepository.save(profileMapper.toEntity(profile)));
    }

    @Override
    public Optional<Profile> findByUserId(UUID userId) {
        return jpaProfileRepository.findByUserId(userId).map(profileMapper::toDomain);
    }
}
