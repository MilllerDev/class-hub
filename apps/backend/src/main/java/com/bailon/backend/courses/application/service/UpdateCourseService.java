package com.bailon.backend.courses.application.service;

import com.bailon.backend.config.Utils;
import com.bailon.backend.courses.application.port.CourseRepository;
import com.bailon.backend.courses.application.usecase.UpdateCourseUseCase;
import com.bailon.backend.courses.domain.exception.CourseAlreadyExistsException;
import com.bailon.backend.courses.domain.exception.CourseNotFoundException;
import com.bailon.backend.courses.domain.model.Course;
import com.bailon.backend.courses.infrastructure.mapper.CourseMapper;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UpdateCourseService implements UpdateCourseUseCase {
    private final CourseRepository courseRepository;
    private final CourseMapper mapper;

    public UpdateCourseService(CourseRepository courseRepository, CourseMapper mapper) {
        this.courseRepository = courseRepository;
        this.mapper = mapper;
    }

    @Override
    public Course execute(UUID id, Course course) {
        course.setSlug(Utils.generateSlug(course.getName()));
        if (courseRepository.alreadyExists(course.getName(), course.getSlug(), id)) {
            throw new CourseAlreadyExistsException("El curso ya existe");
        }
        Course found = courseRepository.findById(id).orElseThrow(
                () -> new CourseNotFoundException("El curso no existe")
        );
        mapper.toDomain(course, found);
        return courseRepository.save(found);
    }
}
