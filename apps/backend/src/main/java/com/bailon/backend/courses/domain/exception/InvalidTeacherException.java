package com.bailon.backend.courses.domain.exception;

import com.bailon.backend.config.exceptions.ApiException;
import org.springframework.http.HttpStatus;

public class InvalidTeacherException extends ApiException {
    public InvalidTeacherException(String message) {
        super(HttpStatus.BAD_REQUEST, message);
    }
}
