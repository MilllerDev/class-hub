package com.bailon.backend.courses.application.usecase;

import com.bailon.backend.courses.domain.model.Course;

public interface CreateCourseUseCase {
    Course execute(Course course, boolean useAi);
}
