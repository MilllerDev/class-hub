package com.bailon.backend.profile.infrastructure.persistence;

import com.bailon.backend.profile.application.port.ProfileRepository;
import com.bailon.backend.profile.domain.model.Profile;
import com.bailon.backend.profile.infrastructure.mapper.ProfileMapper;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public class ProfileRepositoryAdapter implements ProfileRepository {
    private final ProfileRepositoryJpa repositoryJpa;
    private final ProfileMapper mapper;

    public ProfileRepositoryAdapter(ProfileRepositoryJpa repositoryJpa, ProfileMapper mapper) {
        this.repositoryJpa = repositoryJpa;
        this.mapper = mapper;
    }

    @Override
    public Profile save(Profile profile) {
        return mapper.toDomain(repositoryJpa.save(mapper.toEntity(profile)));
    }

    @Override
    public Optional<Profile> findByUserId(UUID userId) {
        return repositoryJpa.findByUserId(userId).map(mapper::toDomain);
    }
}
