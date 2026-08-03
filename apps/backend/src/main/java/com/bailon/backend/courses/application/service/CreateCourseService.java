package com.bailon.backend.courses.application.service;

import com.bailon.backend.config.Utils;
import com.bailon.backend.courses.application.port.CourseAiPort;
import com.bailon.backend.courses.application.port.CourseRepository;
import com.bailon.backend.courses.application.usecase.CreateCourseUseCase;
import com.bailon.backend.courses.domain.exception.CourseAlreadyExistsException;
import com.bailon.backend.courses.domain.exception.InvalidTeacherException;
import com.bailon.backend.courses.domain.model.Course;
import com.bailon.backend.users.application.port.UserRepository;
import com.bailon.backend.users.domain.model.UserRole;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CreateCourseService implements CreateCourseUseCase {
    private final CourseRepository courseRepository;
    private final UserRepository userRepository;
    private final CourseAiPort aiPort;

    public CreateCourseService(CourseRepository courseRepository, UserRepository userRepository, CourseAiPort aiPort) {
        this.courseRepository = courseRepository;
        this.userRepository = userRepository;
        this.aiPort = aiPort;
    }

    @Override
    @Transactional
    public Course execute(Course course, boolean useAi) {
        if (course.getUserId() != null) {
            if (!userRepository.existsUser(course.getUserId(), UserRole.TEACHER)) {
                throw new InvalidTeacherException("No existe un docente al docente");
            }
        }
        course.setSlug(Utils.generateSlug(course.getName()));
        if (courseRepository.alreadyExists(course.getName(), course.getSlug())) {
            throw new CourseAlreadyExistsException(course.getName());
        }
        course.setEnabled(course.getUserId() != null);
        Course saved = courseRepository.save(course);
        if (useAi) {
            aiPort.genereteDetails(saved, course.getName());
        }
        return saved;
    }
}
