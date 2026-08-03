package com.bailon.backend.auth.infrastructure.rest;

import com.bailon.backend.auth.application.usecase.ActivationAccountUseCase;
import com.bailon.backend.auth.application.usecase.LoginUseCase;
import com.bailon.backend.auth.application.usecase.RegisterUseCase;
import com.bailon.backend.auth.application.usecase.ResendActivationUseCase;
import com.bailon.backend.auth.infrastructure.mapper.AuthMapper;
import com.bailon.backend.auth.infrastructure.rest.request.LoginRequest;
import com.bailon.backend.auth.infrastructure.rest.request.RefreshTokenRequest;
import com.bailon.backend.auth.infrastructure.rest.request.RegisterRequest;
import com.bailon.backend.auth.infrastructure.rest.request.ResendActivationRequest;
import com.bailon.backend.config.ApiResponse;
import com.bailon.backend.token.application.usecase.RefreshTokenUseCase;
import com.bailon.backend.token.domain.model.Auth;
import com.bailon.backend.users.domain.model.User;
import com.bailon.backend.users.infrastructure.rest.response.UserResponse;
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
    private final ActivationAccountUseCase activateAccount;
    private final ResendActivationUseCase resendActivation;

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
    public ResponseEntity<ApiResponse<Auth>> activate(@RequestParam String token) {
        return ResponseEntity.ok(
                ApiResponse.success("Se activo la cuenta exitosamente", activateAccount.execute(token))
        );
    }

    @PostMapping("/resend-activation")
    public ResponseEntity<ApiResponse<Void>> resendActivation(@Valid @RequestBody ResendActivationRequest req) {
        resendActivation.execute(req.email());
        return ResponseEntity.ok(
                ApiResponse.success("Se envió con éxito el link de activación", null)
        );
    }
}
