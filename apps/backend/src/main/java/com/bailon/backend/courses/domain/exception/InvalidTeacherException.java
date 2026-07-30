package com.bailon.backend.courses.domain.exception;

public class InvalidTeacherException extends RuntimeException {
    public InvalidTeacherException(String message) {
        super(message);
    }
}
