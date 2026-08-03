package com.bailon.backend.courses.application.usecase;

import java.util.UUID;

public interface DeleteCourseUseCase {
    void execute(UUID id);
}
