package com.bailon.backend.users.domain.exception;

import com.bailon.backend.config.exceptions.ApiException;
import org.springframework.http.HttpStatus;

public class InvalidRoleException extends ApiException {
    public InvalidRoleException(String message) {
        super(HttpStatus.BAD_REQUEST, message);
    }
}
