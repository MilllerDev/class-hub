package com.bailon.backend.users.application.port;

import com.bailon.backend.users.domain.model.User;
import com.bailon.backend.users.domain.model.UserRole;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository {
    User save(User user);

    Optional<User> findByEmail(String email);

    Optional<User> findByEmailPendingActivation(String email);

    Optional<User> findById(UUID id);

    Optional<User> findActive(UUID id);

    Optional<User> findByIdPendingActivation(UUID id);

    boolean existsByEmail(String email);

    boolean hasRole(UUID userId, UserRole role);

    long count();
}
