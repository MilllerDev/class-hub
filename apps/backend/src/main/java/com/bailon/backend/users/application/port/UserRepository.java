package com.bailon.backend.users.application.port;

import com.bailon.backend.users.domain.model.User;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository {
    User save(User user);

    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);

    Optional<User> findById(UUID id);

    Optional<User> findByIdInactive(UUID id);
}
