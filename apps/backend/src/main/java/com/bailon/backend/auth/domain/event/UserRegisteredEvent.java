package com.bailon.backend.auth.domain.event;

public record UserRegisteredEvent(String email, String activationToken) {
}
