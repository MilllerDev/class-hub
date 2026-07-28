package com.bailon.backend.auth.application.service;

import com.bailon.backend.auth.application.usecase.RegisterUseCase;
import com.bailon.backend.auth.domain.event.ActivationEmailRequestedEvent;
import com.bailon.backend.profile.application.port.ProfileRepository;
import com.bailon.backend.profile.domain.model.Profile;
import com.bailon.backend.token.application.service.TokenIssuerService;
import com.bailon.backend.users.application.port.UserRepository;
import com.bailon.backend.users.domain.exception.UserAlreadyExistsException;
import com.bailon.backend.users.domain.model.User;
import com.bailon.backend.users.domain.model.UserRole;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RegisterService implements RegisterUseCase {
    private final ProfileRepository profileRepository;
    private final TokenIssuerService tokenIssuer;
    private final UserRepository userRepository;
    private final ApplicationEventPublisher eventPublisher;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public User execute(Profile profile) {
        if (userRepository.existsByEmail(profile.getUser().getEmail())) {
            throw new UserAlreadyExistsException("El email ya está registrado");
        }
        profile.getUser().setPassword(passwordEncoder.encode(profile.getUser().getPassword()));
        profile.getUser().setRole(UserRole.STUDENT);
        profile.getUser().setEnabled(false);
        Profile savedProfile = profileRepository.save(profile);
        User user = savedProfile.getUser();
        eventPublisher.publishEvent(new ActivationEmailRequestedEvent(user.getEmail(), tokenIssuer.issueTokenActivation(user.getId())));
        return user;
    }
}

