package com.bailon.backend.courses.application.port;

import com.bailon.backend.courses.domain.model.Course;

public interface CourseAiPort {
    void genereteDetails(Course course, String name);
}
