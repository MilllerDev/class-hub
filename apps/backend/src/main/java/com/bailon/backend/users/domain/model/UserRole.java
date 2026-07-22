package com.bailon.backend.users.domain.model;

import org.springframework.security.core.GrantedAuthority;

public enum UserRole implements GrantedAuthority {
    TEACHER,
    STUDENT,
    ADMIN;

    @Override
    public String getAuthority() {
        return this.name();
    }
}
