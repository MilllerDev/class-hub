package com.bailon.backend.courses.application.usecase;

import com.bailon.backend.courses.domain.model.Course;
import com.bailon.backend.users.domain.model.UserRole;

public interface GetCourseUseCase {
    Course execute(String slug, UserRole role);
}
