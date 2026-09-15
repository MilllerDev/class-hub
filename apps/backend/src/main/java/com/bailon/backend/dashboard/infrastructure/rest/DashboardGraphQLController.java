package com.bailon.backend.dashboard.infrastructure.rest;

import com.bailon.backend.chat.application.usecase.ListChatUseCase;
import com.bailon.backend.chat.domain.model.ChatMessage;
import com.bailon.backend.courses.application.usecase.GetAllCoursesUseCase;
import com.bailon.backend.courses.domain.model.Course;
import com.bailon.backend.dashboard.application.usecase.DashboardUseCase;
import com.bailon.backend.dashboard.domain.model.Dashboard;
import com.bailon.backend.users.infrastructure.security.AppUserPrincipal;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class DashboardGraphQLController {
    private final DashboardUseCase dashboardUseCase;
    private final GetAllCoursesUseCase getCourses;
    private final ListChatUseCase getMessages;

    public DashboardGraphQLController(DashboardUseCase dashboardUseCase, GetAllCoursesUseCase getCourses, ListChatUseCase getMessages) {
        this.dashboardUseCase = dashboardUseCase;
        this.getCourses = getCourses;
        this.getMessages = getMessages;
    }

    @QueryMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public Dashboard dashboard() {
        return dashboardUseCase.execute();
    }

    @QueryMapping
    public List<Course> lastCourses(@AuthenticationPrincipal AppUserPrincipal auth) {
        return getCourses.execute(auth.user().getRole(), auth.user().getId());
    }

    @QueryMapping
    @PreAuthorize("hasAuthority('STUDENT')")
    public List<ChatMessage> lastChats(@AuthenticationPrincipal AppUserPrincipal auth) {
        return getMessages.last(auth.user().getId());
    }
}
