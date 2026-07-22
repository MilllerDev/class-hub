package com.bailon.backend.auth.infrastructure.rest;

import com.bailon.backend.auth.application.service.ActivateAccountService;
import com.bailon.backend.auth.application.usecase.LoginUseCase;
import com.bailon.backend.auth.application.usecase.RegisterUseCase;
import com.bailon.backend.auth.domain.model.Auth;
import com.bailon.backend.auth.infrastructure.mapper.AuthMapper;
import com.bailon.backend.auth.infrastructure.rest.request.LoginRequest;
import com.bailon.backend.auth.infrastructure.rest.request.RefreshTokenRequest;
import com.bailon.backend.auth.infrastructure.rest.request.RegisterRequest;
import com.bailon.backend.config.ApiResponse;
import com.bailon.backend.token.application.usecase.RefreshTokenUseCase;
import com.bailon.backend.users.domain.model.User;
import com.bailon.backend.users.infrastructure.persistence.UserResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final RegisterUseCase register;
    private final LoginUseCase login;
    private final AuthMapper mapper;
    private final RefreshTokenUseCase refresh;
    private final ActivateAccountService activateAccountService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<UserResponse>> register(@Valid @RequestBody RegisterRequest req) {
        User user = register.execute(mapper.toDomain(req));
        return ResponseEntity.ok(
                ApiResponse.success("Registro exitoso", mapper.toDto(user))
        );
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<Auth>> login(@Valid @RequestBody LoginRequest req) {
        return ResponseEntity.ok(
                ApiResponse.success("Inicio de sesión correcto", login.execute(req.email(), req.password()))
        );
    }

    @PostMapping("/refresh")
    public ResponseEntity<ApiResponse<Auth>> refresh(@Valid @RequestBody RefreshTokenRequest req) {
        return ResponseEntity.ok(
                ApiResponse.success("Refresco exitoso", refresh.execute(req.refreshToken()))
        );
    }

    @GetMapping("/activate")
    public ResponseEntity<ApiResponse<Void>> activate(@RequestParam String token) {
        activateAccountService.execute(token);
        return ResponseEntity.ok(ApiResponse.success("null", null));
    }
}
