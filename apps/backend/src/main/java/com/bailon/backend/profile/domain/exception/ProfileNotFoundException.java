package com.bailon.backend.profile.domain.exception;

import com.bailon.backend.config.exceptions.ApiException;
import org.springframework.http.HttpStatus;

public class ProfileNotFoundException extends ApiException {
    public ProfileNotFoundException() {
        super(HttpStatus.NOT_FOUND, "Perfil no encontrado");
    }
}
