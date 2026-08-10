package com.bailon.backend.users.infrastructure.persistence;

import com.bailon.backend.users.application.port.UserRepository;
import com.bailon.backend.users.domain.model.User;
import com.bailon.backend.users.domain.model.UserRole;
import com.bailon.backend.users.infrastructure.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class UserRepositoryAdapter implements UserRepository {
    private final UserRepositoryJpa repositoryJpa;
    private final UserMapper mapper;

    @Override
    public User save(User user) {
        return mapper.toDomain(repositoryJpa.save(mapper.toEntity(user)));
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return repositoryJpa.findByEmail(email).map(mapper::toDomain);
    }

    @Override
    public Optional<User> findByEmailPendingActivation(String email) {
        return repositoryJpa.findByEmailAndEnabledFalse(email).map(mapper::toDomain);
    }

    @Override
    public boolean existsByEmail(String email) {
        return repositoryJpa.existsByEmail(email);
    }

    @Override
    public boolean hasRole(UUID userId, UserRole role) {
        return repositoryJpa.existsByIdAndRole(userId, role);
    }

    @Override
    public Optional<User> findById(UUID id) {
        return repositoryJpa.findById(id).map(mapper::toDomain);
    }

    @Override
    public Optional<User> findByIdPendingActivation(UUID id) {
        return repositoryJpa.findByIdAndEnabledFalse(id).map(mapper::toDomain);
    }

    @Override
    public long count() {
        return repositoryJpa.count();
    }
}
