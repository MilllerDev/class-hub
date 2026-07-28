package com.bailon.backend.auth.application.service;

import com.bailon.backend.auth.application.usecase.ResendActivationUseCase;
import com.bailon.backend.auth.domain.event.ActivationEmailRequestedEvent;
import com.bailon.backend.token.application.service.TokenIssuerService;
import com.bailon.backend.users.application.port.UserRepository;
import com.bailon.backend.users.domain.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ResendActivationService implements ResendActivationUseCase {
    private final UserRepository userRepository;
    private final TokenIssuerService tokenIssuer;
    private final ApplicationEventPublisher eventPublisher;

    @Override
    @Transactional
    public void execute(String email) {
        User user = userRepository.findByEmailInactive(email).orElse(null);
        if (user == null) {
            return;
        }
        String token = tokenIssuer.issueTokenActivation(user.getId());
        eventPublisher.publishEvent(new ActivationEmailRequestedEvent(user.getEmail(), token));
    }
}
