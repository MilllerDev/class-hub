package com.bailon.backend.users.domain.exception;

import com.bailon.backend.config.exceptions.ApiException;
import org.springframework.http.HttpStatus;

public class AccountAlreadyActiveException extends ApiException {
    public AccountAlreadyActiveException(String message) {
        super(HttpStatus.CONFLICT, message);
    }
}
