package com.bailon.backend.courses.domain.exception;

public class CourseNotFoundException extends RuntimeException {
    public CourseNotFoundException(String slug) {
        super("Curso no encontrado: " + slug);
    }
}
