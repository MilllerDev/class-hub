package com.bailon.backend.users.domain.model;

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
}
