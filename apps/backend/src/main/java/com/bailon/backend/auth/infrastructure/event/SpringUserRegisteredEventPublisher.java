package com.bailon.backend.auth.infrastructure.event;

import com.bailon.backend.auth.application.port.UserRegisteredEventPort;
import com.bailon.backend.auth.domain.event.UserRegisteredEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class SpringUserRegisteredEventPublisher implements UserRegisteredEventPort {
    private final ApplicationEventPublisher eventPublisher;

    public SpringUserRegisteredEventPublisher(ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    @Override
    public void publish(UserRegisteredEvent event) {
        eventPublisher.publishEvent(event);
    }
}
