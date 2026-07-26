package com.bailon.backend.auth.domain.event;

public record ActivationEmailRequestedEvent(String email, String token) {
}
