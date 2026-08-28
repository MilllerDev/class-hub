package com.bailon.backend.courses.application.service;

import com.bailon.backend.config.Utils;
import com.bailon.backend.courses.application.port.CourseAiPort;
import com.bailon.backend.courses.application.port.CourseRepository;
import com.bailon.backend.courses.application.usecase.UpdateCourseUseCase;
import com.bailon.backend.courses.domain.exception.CourseAlreadyExistsException;
import com.bailon.backend.courses.domain.exception.CourseNotFoundException;
import com.bailon.backend.courses.domain.exception.InvalidTeacherException;
import com.bailon.backend.courses.domain.model.Course;
import com.bailon.backend.courses.infrastructure.mapper.CourseMapper;
import com.bailon.backend.users.application.port.UserRepository;
import com.bailon.backend.users.domain.model.UserRole;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UpdateCourseService implements UpdateCourseUseCase {
    private final CourseRepository courseRepository;
    private final CourseAiPort courseAiPort;
    private final UserRepository userRepository;
    private final CourseMapper mapper;

    @Override
    @Transactional
    public Course execute(UUID id, Course course, boolean useAi) {
        Course found = courseRepository.findById(id).orElseThrow(
                () -> new CourseNotFoundException("El curso no existe")
        );
        if (course.getUserId() != null) {
            if (!userRepository.hasRole(course.getUserId(), UserRole.TEACHER)) {
                throw new InvalidTeacherException("No existe el docente");
            }
        }
        if (course.getName() != null && !course.getName().equals(found.getName())) {
            String newSlug = Utils.generateSlug(course.getName());
            if (courseRepository.existsByNameOrSlug(course.getName(), newSlug, id)) {
                throw new CourseAlreadyExistsException(course.getName());
            }
            found.setSlug(newSlug);
        }
        mapper.toDomain(course, found);
        found.setEnabled(found.getUserId() != null);
        Course updated = courseRepository.save(found);
        if (useAi) {
            courseAiPort.generateDetails(updated);
        }
        return updated;
    }
}