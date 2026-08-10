package com.bailon.backend.users.domain.model;

import com.bailon.backend.users.domain.exception.AccountAlreadyActiveException;
import com.bailon.backend.users.domain.exception.AccountAlreadyDeactivatedException;
import com.bailon.backend.users.domain.exception.RoleAlreadyAssignedException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private UUID id;
    private String email;
    private String password;
    private UserRole role;
    private boolean enabled;
    private LocalDateTime lastLogin;
    private LocalDateTime createdAt;

    public static User createNewStudent(String email, String encodedPassword) {
        User user = new User();
        user.email = email;
        user.password = encodedPassword;
        user.role = UserRole.STUDENT;
        user.enabled = false;
        return user;
    }

    public void activate() {
        if (this.enabled) {
            throw new AccountAlreadyActiveException("La cuenta ya está activa");
        }
        this.enabled = true;
    }

    public void deactivate() {
        if (!this.enabled) {
            throw new AccountAlreadyDeactivatedException("La cuenta ya está desactivada");
        }
        this.enabled = false;
    }

    public void changeRole(UserRole role) {
        if (this.role == role) {
            throw new RoleAlreadyAssignedException("El usuario ya tiene el rol: " + role.name().toLowerCase());
        }
        this.role = role;
    }

    public void recordLogin() {
        this.lastLogin = LocalDateTime.now();
    }
}
