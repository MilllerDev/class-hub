package com.bailon.backend.courses.infrastructure.rest;

import com.bailon.backend.courses.application.usecase.GetAllCoursesUseCase;
import com.bailon.backend.courses.domain.model.Course;
import com.bailon.backend.profile.application.usecase.GetProfileUseCase;
import com.bailon.backend.profile.domain.model.Profile;
import com.bailon.backend.users.infrastructure.security.AppUserPrincipal;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class CourseGraphQLController {
    private final GetAllCoursesUseCase getAllCourses;
    private final GetProfileUseCase getProfile;

    public CourseGraphQLController(GetAllCoursesUseCase getAllCourses, GetProfileUseCase getProfile) {
        this.getAllCourses = getAllCourses;
        this.getProfile = getProfile;
    }

    @QueryMapping
    public List<Course> courses(@AuthenticationPrincipal AppUserPrincipal auth) {
        return getAllCourses.execute(auth.user().getRole(), auth.user().getId());
    }

    @SchemaMapping(typeName = "Course", field = "profile")
    public Profile profile(Course course) {
        return course.getUserId() != null ? getProfile.execute(course.getUserId()) : null;
    }
}