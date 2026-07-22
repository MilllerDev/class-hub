package com.bailon.backend.auth.infrastructure.rest.response;

public record AuthResponse(String accessToken, String refreshToken) {
}
