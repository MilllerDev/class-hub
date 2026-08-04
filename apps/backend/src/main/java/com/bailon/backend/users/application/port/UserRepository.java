package com.bailon.backend.users.application.port;

import com.bailon.backend.users.domain.model.User;
import com.bailon.backend.users.domain.model.UserRole;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository {
    User save(User user);

    Optional<User> findByEmail(String email);

    Optional<User> findByEmailInactive(String email);

    boolean existsByEmail(String email);

    boolean existsUser(UUID id, UserRole role);

    Optional<User> findById(UUID id);

    Optional<User> findByIdInactive(UUID id);

    long count();
}
