package com.bailon.backend.token.domain.exception;

import com.bailon.backend.config.exceptions.ApiException;
import org.springframework.http.HttpStatus;

public class InvalidRefreshTokenException extends ApiException {
    public InvalidRefreshTokenException() {
        super(HttpStatus.UNAUTHORIZED, "Token inválido o expirado");
    }
}
