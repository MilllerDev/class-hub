package com.bailon.backend.profile.infrastructure.persistence;

import com.bailon.backend.profile.application.port.ProfileRepository;
import com.bailon.backend.profile.domain.model.Profile;
import com.bailon.backend.profile.infrastructure.mapper.ProfileMapper;
import com.bailon.backend.users.domain.model.UserRole;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
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
    public Optional<Profile> findByUser(UUID userId) {
        return repositoryJpa.findByUserId(userId).map(mapper::toDomain);
    }

    @Override
    public Optional<Profile> findActiveUser(UUID userId) {
        return repositoryJpa.findByUserIdAndUserEnabledTrue(userId).map(mapper::toDomain);
    }

    @Override
    public List<Profile> findByUserIds(Collection<UUID> userIds) {
        return repositoryJpa.findByUserIdIn(userIds).stream().map(mapper::toDomain).toList();
    }

    @Override
    public List<Profile> profilesByRole(UserRole role, UUID userId) {
        List<ProfileEntity> entities = role == null
                ? repositoryJpa.findByUserIdNot(userId)
                : repositoryJpa.findByUserRoleAndUserIdNot(role, userId);
        return entities.stream().map(mapper::toDomain).toList();
    }

    @Override
    public Page<Profile> search(UUID userId, String query, UserRole role, Pageable pageable) {
        return repositoryJpa.search(userId, query, role, pageable).map(mapper::toDomain);
    }

    @Override
    public List<Profile> findLatest() {
        return repositoryJpa.findTop5ByOrderByUserCreatedAtDesc().stream().map(
                mapper::toDomain
        ).toList();
    }
}
