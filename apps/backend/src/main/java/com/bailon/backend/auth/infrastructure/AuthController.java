package com.bailon.backend.auth.infrastructure;

import com.bailon.backend.auth.infrastructure.dto.LoginRequest;
import com.bailon.backend.config.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<Void>> registerUser(String name) {
        return null;
    }

    @GetMapping("/login")
    public ResponseEntity<ApiResponse<?>> registerUser(@RequestBody LoginRequest dto) {
        return ResponseEntity.ok(
                ApiResponse.success(null, "Login successful!")
        );
    }
}
