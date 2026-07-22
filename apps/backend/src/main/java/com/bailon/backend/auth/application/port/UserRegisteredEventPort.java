package com.bailon.backend.auth.application.port;

import com.bailon.backend.auth.domain.event.UserRegisteredEvent;

public interface UserRegisteredEventPort {
    void publish(UserRegisteredEvent event);
}
