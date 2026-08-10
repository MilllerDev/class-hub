package com.bailon.backend.users.domain.exception;

import com.bailon.backend.config.exceptions.ApiException;
import org.springframework.http.HttpStatus;

public class AccountAlreadyDeactivatedException extends ApiException {
    public AccountAlreadyDeactivatedException(String message) {
        super(HttpStatus.CONFLICT, message);
    }
}
