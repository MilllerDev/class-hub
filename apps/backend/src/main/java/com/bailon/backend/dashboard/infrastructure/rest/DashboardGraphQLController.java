package com.bailon.backend.dashboard.infrastructure.rest;

import com.bailon.backend.dashboard.application.usecase.DashboardUseCase;
import com.bailon.backend.dashboard.domain.model.Dashboard;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;

@Controller
public class DashboardGraphQLController {
    private final DashboardUseCase dashboardUseCase;

    public DashboardGraphQLController(DashboardUseCase dashboardUseCase) {
        this.dashboardUseCase = dashboardUseCase;
    }

    @QueryMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public Dashboard dashboard() {
        return dashboardUseCase.execute();
    }
}
