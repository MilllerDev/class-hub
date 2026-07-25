package com.bailon.backend.auth.domain.event;

public record UserRegisteredEvent(String name, String email, String activationToken) {
}
