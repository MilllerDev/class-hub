package com.bailon.backend.courses.domain.exception;

import com.bailon.backend.config.exceptions.ApiException;
import org.springframework.http.HttpStatus;

public class CourseNotFoundException extends ApiException {
    public CourseNotFoundException(String message) {
        super(HttpStatus.NOT_FOUND, message);
    }
}
