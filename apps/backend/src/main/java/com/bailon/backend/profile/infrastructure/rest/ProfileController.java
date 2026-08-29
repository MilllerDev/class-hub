package com.bailon.backend.profile.infrastructure.rest;

import com.bailon.backend.config.ApiResponse;
import com.bailon.backend.config.PageResponse;
import com.bailon.backend.profile.application.usecase.GetProfileUseCase;
import com.bailon.backend.profile.application.usecase.GetProfilesUseCase;
import com.bailon.backend.profile.application.usecase.UpdateProfileUseCase;
import com.bailon.backend.profile.domain.model.Profile;
import com.bailon.backend.profile.infrastructure.mapper.ProfileMapper;
import com.bailon.backend.profile.infrastructure.rest.request.PartialProfileRequest;
import com.bailon.backend.profile.infrastructure.rest.response.ProfileResponse;
import com.bailon.backend.users.domain.model.UserRole;
import com.bailon.backend.users.infrastructure.security.AppUserPrincipal;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/profiles")
@RequiredArgsConstructor
public class ProfileController {
    private final GetProfileUseCase getProfile;
    private final GetProfilesUseCase getProfiles;
    private final UpdateProfileUseCase updateProfile;
    private final ProfileMapper mapper;

    @GetMapping
    public ResponseEntity<ApiResponse<ProfileResponse>> getProfile(@AuthenticationPrincipal AppUserPrincipal auth) {
        Profile profile = getProfile.me(auth.user().getId());
        return ResponseEntity.ok(
                ApiResponse.success("Perfil encontrado", mapper.toDto(profile))
        );
    }

    @GetMapping("/{userId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ApiResponse<ProfileResponse>> getProfile(@PathVariable UUID userId) {
        Profile profile = getProfile.other(userId);
        return ResponseEntity.ok(
                ApiResponse.success("Perfil encontrado", mapper.toDto(profile))
        );
    }

    @GetMapping("/list")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ApiResponse<PageResponse<ProfileResponse>>> getProfiles(
            @AuthenticationPrincipal AppUserPrincipal auth,
            @RequestParam(required = false) String q,
            @RequestParam(required = false) UserRole role,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Page<Profile> profiles = getProfiles.page(auth.user().getId(), q, role, PageRequest.of(page, size));
        PageResponse<ProfileResponse> response = PageResponse.of(profiles.map(mapper::toDto));
        return ResponseEntity.ok(ApiResponse.success("Listado de usuarios", response));
    }

    @GetMapping("/role")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ApiResponse<List<ProfileResponse>>> getProfilesByRole(
            @AuthenticationPrincipal AppUserPrincipal auth,
            @RequestParam(required = false) UserRole role
    ) {
        List<Profile> profiles = getProfiles.list(auth.user().getId(), role);
        return ResponseEntity.ok(
                ApiResponse.success("Listado de usuarios", profiles.stream().map(mapper::toDto).toList())
        );
    }

    @PatchMapping
    public ResponseEntity<ApiResponse<ProfileResponse>> updateProfile(
            @AuthenticationPrincipal AppUserPrincipal auth, @Valid @RequestBody PartialProfileRequest request
    ) {
        Profile found = getProfile.me(auth.user().getId());
        mapper.toDomain(request, found);
        Profile profile = updateProfile.execute(auth.user().getId(), found);
        return ResponseEntity.ok(
                ApiResponse.success("Perfil actualizado", mapper.toDto(profile))
        );
    }
}