package com.bailon.backend.courses.application.usecase;

import com.bailon.backend.courses.domain.model.Course;
import com.bailon.backend.users.domain.model.UserRole;

import java.util.List;
import java.util.UUID;

public interface GetAllCoursesUseCase {
    List<Course> execute(UserRole role, UUID userId);
}
