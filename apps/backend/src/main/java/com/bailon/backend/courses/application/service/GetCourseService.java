package com.bailon.backend.courses.application.service;

import com.bailon.backend.courses.application.port.CourseRepository;
import com.bailon.backend.courses.application.usecase.GetCourseUseCase;
import com.bailon.backend.courses.domain.exception.CourseNotFoundException;
import com.bailon.backend.courses.domain.model.Course;
import com.bailon.backend.users.domain.model.UserRole;
import org.springframework.stereotype.Service;

@Service
public class GetCourseService implements GetCourseUseCase {
    private final CourseRepository courseRepository;

    public GetCourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    @Override
    public Course execute(String slug, UserRole role) {
        if (role != UserRole.ADMIN) {
            return courseRepository.findEnabledBySlug(slug).orElseThrow(
                    () -> new CourseNotFoundException("No se encontró el curso o no tienes acceso.")
            );
        }
        return courseRepository.findBySlug(slug).orElseThrow(
                () -> new CourseNotFoundException("No se encontró el curso.")
        );
    }
}
