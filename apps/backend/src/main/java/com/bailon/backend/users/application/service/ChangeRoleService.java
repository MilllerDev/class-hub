package com.bailon.backend.users.application.service;

import com.bailon.backend.users.application.port.UserRepository;
import com.bailon.backend.users.application.usecase.ChangeRoleUseCase;
import com.bailon.backend.users.domain.exception.InvalidRoleException;
import com.bailon.backend.users.domain.exception.UserNotFoundException;
import com.bailon.backend.users.domain.model.User;
import com.bailon.backend.users.domain.model.UserRole;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ChangeRoleService implements ChangeRoleUseCase {
    private final UserRepository userRepository;

    public ChangeRoleService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User execute(UUID userId, UserRole role) {
        if (role == null) {
            throw new InvalidRoleException("El rol no puede ser nulo");
        }
        User user = userRepository.findById(userId).orElseThrow(
                () -> new UserNotFoundException("No se encontró el usuario")
        );
        user.changeRole(role);
        return userRepository.save(user);
    }
}
