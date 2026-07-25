package com.bailon.backend.users.infrastructure.persistence;

import com.bailon.backend.users.application.port.UserRepository;
import com.bailon.backend.users.domain.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class UserRepositoryAdapter implements UserRepository {
    private final JpaUserRepository repositoryJpa;
    private final UserMapper userMapper;

    @Override
    public User save(User user) {
        return userMapper.toDomain(repositoryJpa.save(userMapper.toEntity(user)));
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return repositoryJpa.findByEmail(email).map(userMapper::toDomain);
    }

    @Override
    public boolean existsByEmail(String email) {
        return repositoryJpa.existsByEmail(email);
    }

    @Override
    public Optional<User> findById(UUID id) {
        return repositoryJpa.findById(id).map(userMapper::toDomain);
    }

    @Override
    public Optional<User> findByIdInactive(UUID id) {
        return repositoryJpa.findByIdAndEnabledFalse(id).map(userMapper::toDomain);
    }
}
