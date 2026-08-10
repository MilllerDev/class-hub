package com.bailon.backend.courses.domain.exception;

import com.bailon.backend.config.exceptions.ApiException;
import org.springframework.http.HttpStatus;

public class CourseAlreadyExistsException extends ApiException {
    public CourseAlreadyExistsException(String message) {
        super(HttpStatus.CONFLICT, "El curso: " + message + " ya existe");
    }
}
