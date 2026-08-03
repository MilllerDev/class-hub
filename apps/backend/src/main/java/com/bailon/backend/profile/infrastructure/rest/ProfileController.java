package com.bailon.backend.profile.infrastructure.rest;

import com.bailon.backend.config.ApiResponse;
import com.bailon.backend.profile.application.usecase.GetProfileUseCase;
import com.bailon.backend.profile.application.usecase.GetProfilesUseCase;
import com.bailon.backend.profile.domain.model.Profile;
import com.bailon.backend.profile.infrastructure.mapper.ProfileMapper;
import com.bailon.backend.profile.infrastructure.rest.response.ProfileResponse;
import com.bailon.backend.users.infrastructure.security.AppUserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/profiles")
@RequiredArgsConstructor
public class ProfileController {
    private final GetProfileUseCase getProfile;
    private final GetProfilesUseCase getProfiles;
    private final ProfileMapper mapper;

    @GetMapping
    public ResponseEntity<ApiResponse<ProfileResponse>> getProfile(@AuthenticationPrincipal AppUserPrincipal auth) {
        return ResponseEntity.ok(
                ApiResponse.success("Perfil encontrado", mapper.toDto(getProfile.execute(auth.user().getId())))
        );
    }

    @GetMapping("/list")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ApiResponse<List<ProfileResponse>>> getProfiles(@AuthenticationPrincipal AppUserPrincipal auth) {
        List<Profile> profiles = getProfiles.list(auth.user().getId());
        return ResponseEntity.ok(
                ApiResponse.success("Listado de usuarios", profiles.stream().map(mapper::toDto).toList())
        );
    }
}
