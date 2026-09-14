package com.bailon.backend.dashboard.infrastructure.rest;

import com.bailon.backend.chat.application.usecase.ListChatUseCase;
import com.bailon.backend.chat.domain.model.ChatMessage;
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
    private final ListChatUseCase getMessages;

    public DashboardGraphQLController(DashboardUseCase dashboardUseCase, ListChatUseCase getMessages) {
        this.dashboardUseCase = dashboardUseCase;
        this.getMessages = getMessages;
    }

    @QueryMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public Dashboard dashboard() {
        return dashboardUseCase.execute();
    }

    @QueryMapping
    @PreAuthorize("hasAuthority('STUDENT')")
    public List<ChatMessage> lastChats(@AuthenticationPrincipal AppUserPrincipal auth) {
        return getMessages.last(auth.user().getId());
    }
}
