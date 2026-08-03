package com.bailon.backend.auth.domain.exception;

import com.bailon.backend.config.exceptions.ApiException;
import org.springframework.http.HttpStatus;

public class InvalidActivationTokenException extends ApiException {
    public InvalidActivationTokenException(String message) {
        super(HttpStatus.UNAUTHORIZED, message);
    }
}
