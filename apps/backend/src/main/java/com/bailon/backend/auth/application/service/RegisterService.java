package com.bailon.backend.auth.application.service;

import com.bailon.backend.auth.application.port.UserRegisteredEventPort;
import com.bailon.backend.auth.application.usecase.RegisterUseCase;
import com.bailon.backend.auth.domain.event.UserRegisteredEvent;
import com.bailon.backend.config.security.jwt.JwtPort;
import com.bailon.backend.profile.application.port.ProfileRepository;
import com.bailon.backend.profile.domain.model.Profile;
import com.bailon.backend.users.application.port.UserRepository;
import com.bailon.backend.users.domain.exception.UserAlreadyExistsException;
import com.bailon.backend.users.domain.model.User;
import com.bailon.backend.users.domain.model.UserRole;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class RegisterService implements RegisterUseCase {
    private final JwtPort jwtPort;
    private final ProfileRepository profileRepository;
    private final UserRepository userRepository;
    private final UserRegisteredEventPort registeredEventPort;
    private final PasswordEncoder passwordEncoder;

    @Override
    public User execute(Profile profile) {
        if (userRepository.existsByEmail(profile.getUser().getEmail())) {
            throw new UserAlreadyExistsException("Email already registered");
        }
        profile.getUser().setPassword(passwordEncoder.encode(profile.getUser().getPassword()));
        profile.getUser().setRole(UserRole.STUDENT);
        profile.getUser().setEnabled(false);
        Profile savedProfile = profileRepository.save(profile);
        User user = savedProfile.getUser();
        String token = jwtPort.generateJwtActivation(user);
        String verificationUrl = token + "url";
        registeredEventPort.publish(new UserRegisteredEvent(user.getEmail(), verificationUrl));
        return user;
    }
}

