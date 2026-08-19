package com.bailon.backend.topics.domain.exception;

import com.bailon.backend.config.exceptions.ApiException;
import org.springframework.http.HttpStatus;

public class CourseAccessDeniedException extends ApiException {
    public CourseAccessDeniedException(String message) {
        super(HttpStatus.FORBIDDEN, message);
    }
}
