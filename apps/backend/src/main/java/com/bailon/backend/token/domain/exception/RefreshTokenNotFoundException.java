package com.bailon.backend.token.domain.exception;

import com.bailon.backend.config.exceptions.ApiException;
import org.springframework.http.HttpStatus;

public class RefreshTokenNotFoundException extends ApiException {
    public RefreshTokenNotFoundException(String message) {
        super(HttpStatus.UNAUTHORIZED, message);
    }
}
