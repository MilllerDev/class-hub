package com.bailon.backend.users.infrastructure.rest;

import com.bailon.backend.config.ApiResponse;
import com.bailon.backend.users.application.usecase.ChangeAccountStatusUseCase;
import com.bailon.backend.users.application.usecase.ChangeRoleUseCase;
import com.bailon.backend.users.infrastructure.mapper.UserMapper;
import com.bailon.backend.users.infrastructure.rest.request.ChangeAccountStatusRequest;
import com.bailon.backend.users.infrastructure.rest.request.ChangeRoleRequest;
import com.bailon.backend.users.infrastructure.rest.response.UserResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final ChangeRoleUseCase changeRole;
    private final ChangeAccountStatusUseCase changeAccountStatus;
    private final UserMapper mapper;

    @PatchMapping("/{id}/role")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ApiResponse<UserResponse>> changeRole(
            @PathVariable UUID id, @Valid @RequestBody ChangeRoleRequest req
    ) {
        UserResponse response = mapper.toDto(changeRole.execute(id, req.role()));
        return ResponseEntity.ok(
                ApiResponse.success("Rol cambiado a: " + req.role().name().toLowerCase(), response)
        );
    }

    @PatchMapping("/{id}/status")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ApiResponse<UserResponse>> changeStatus(
            @PathVariable UUID id, @Valid @RequestBody ChangeAccountStatusRequest req
    ) {
        UserResponse response = mapper.toDto(changeAccountStatus.execute(id, req.enabled()));
        String message = "Cuenta " + (req.enabled() ? "activada" : "desactivada") + " del usuario: " + response.email();
        return ResponseEntity.ok(ApiResponse.success(message, response));
    }
}
