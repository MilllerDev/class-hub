package com.bailon.backend.courses.application.usecase;

import com.bailon.backend.courses.domain.model.Course;

import java.util.UUID;

public interface UpdateCourseUseCase {
    Course execute(UUID id, Course course);
}
