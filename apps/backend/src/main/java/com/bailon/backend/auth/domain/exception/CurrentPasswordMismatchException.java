package com.bailon.backend.auth.domain.exception;

import com.bailon.backend.config.exceptions.ApiException;
import org.springframework.http.HttpStatus;

public class CurrentPasswordMismatchException extends ApiException {
    public CurrentPasswordMismatchException() {
        super(HttpStatus.BAD_REQUEST, "La contraseña actual no es correcta");
    }
}
