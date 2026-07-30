package com.bailon.backend.courses.application.service;

import com.bailon.backend.courses.application.port.CourseRepository;
import com.bailon.backend.courses.application.usecase.DeleteCourseUseCase;
import com.bailon.backend.courses.domain.exception.CourseNotFoundException;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class DeleteCourseService implements DeleteCourseUseCase {
    private final CourseRepository courseRepository;

    public DeleteCourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    @Override
    public void execute(UUID id) {
        if (courseRepository.exists(id)) {
            throw new CourseNotFoundException("No se encontró el curso");
        }
        courseRepository.delete(id);
    }
}
