package com.bailon.backend.profile.infrastructure.rest;

import com.bailon.backend.profile.application.usecase.GetProfilesUseCase;
import com.bailon.backend.profile.domain.model.Profile;
import com.bailon.backend.users.infrastructure.security.AppUserPrincipal;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class ProfileGraphQLController {
    private final GetProfilesUseCase getProfiles;

    public ProfileGraphQLController(GetProfilesUseCase getProfiles) {
        this.getProfiles = getProfiles;
    }

    @QueryMapping
    public List<Profile> profiles(@AuthenticationPrincipal AppUserPrincipal auth) {
        return getProfiles.list(auth.user().getId());
    }
}
