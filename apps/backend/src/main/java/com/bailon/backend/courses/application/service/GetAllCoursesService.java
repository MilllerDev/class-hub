package com.bailon.backend.courses.application.service;

import com.bailon.backend.courses.application.port.CourseRepository;
import com.bailon.backend.courses.application.usecase.GetAllCoursesUseCase;
import com.bailon.backend.courses.domain.model.Course;
import com.bailon.backend.users.domain.model.UserRole;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class GetAllCoursesService implements GetAllCoursesUseCase {
    private final CourseRepository courseRepository;

    public GetAllCoursesService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    @Override
    public List<Course> execute(UserRole role, UUID userId) {
        return switch (role) {
            case ADMIN -> courseRepository.findAll();
            case TEACHER -> courseRepository.findAllOwnedBy(userId);
            default -> courseRepository.findAllActive();
        };
    }
}
