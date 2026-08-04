package com.bailon.backend.courses.infrastructure.rest;

import com.bailon.backend.courses.application.usecase.GetAllCoursesUseCase;
import com.bailon.backend.courses.application.usecase.GetCourseUseCase;
import com.bailon.backend.courses.domain.model.Course;
import com.bailon.backend.users.infrastructure.security.AppUserPrincipal;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class CourseGraphQLController {
    private final GetAllCoursesUseCase getAllCourses;
    private final GetCourseUseCase getCourse;

    public CourseGraphQLController(GetAllCoursesUseCase getAllCourses, GetCourseUseCase getCourse) {
        this.getAllCourses = getAllCourses;
        this.getCourse = getCourse;
    }

    @QueryMapping
    public List<Course> courses(@AuthenticationPrincipal AppUserPrincipal auth) {
        return getAllCourses.execute(auth.user().getRole(), auth.user().getId());
    }

    @QueryMapping
    public Course course(@AuthenticationPrincipal AppUserPrincipal auth, @Argument String slug) {
        return getCourse.execute(slug, auth.user().getRole());
    }
}
