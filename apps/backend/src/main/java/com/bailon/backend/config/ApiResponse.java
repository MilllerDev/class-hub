package com.bailon.backend.config;

import java.time.LocalDateTime;

public record ApiResponse<T>(
        T data,
        boolean success,
        String message,
        LocalDateTime timestamp
) {
    public static <T> ApiResponse<T> success(String message, T data) {
        return new ApiResponse<>(data, true, message, LocalDateTime.now());
    }

    public static <T> ApiResponse<T> error(String message) {
        return new ApiResponse<>(null, false, message, LocalDateTime.now());
    }
}
