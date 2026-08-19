package com.bailon.backend.courses.infrastructure.graphql;

import com.bailon.backend.courses.application.usecase.GetAllCoursesUseCase;
import com.bailon.backend.courses.domain.model.Course;
import com.bailon.backend.profile.domain.model.Profile;
import com.bailon.backend.users.infrastructure.security.AppUserPrincipal;
import org.dataloader.DataLoader;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Controller
public class CourseGraphQLController {
    private final GetAllCoursesUseCase getAllCourses;

    public CourseGraphQLController(GetAllCoursesUseCase getAllCourses) {
        this.getAllCourses = getAllCourses;
    }

    @QueryMapping
    public List<Course> courses(@AuthenticationPrincipal AppUserPrincipal auth) {
        return getAllCourses.execute(auth.user().getRole(), auth.user().getId());
    }

    @SchemaMapping(typeName = "Course", field = "profile")
    public CompletableFuture<Profile> profile(Course course, DataLoader<UUID, Profile> dataLoader) {
        return course.getUserId() != null ? dataLoader.load(course.getUserId()) : null;
    }
}