package com.bailon.backend.courses.domain.exception;

public class CourseNotFoundException extends RuntimeException {
    public CourseNotFoundException(String message) {
        super(message);
    }
}
