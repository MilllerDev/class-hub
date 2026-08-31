package com.bailon.backend.auth.domain.exception;

import com.bailon.backend.config.exceptions.ApiException;
import org.springframework.http.HttpStatus;

public class PasswordMismatchException extends ApiException {
    public PasswordMismatchException() {
        super(HttpStatus.BAD_REQUEST, "Las contraseñas nuevas no coinciden");
    }
}
