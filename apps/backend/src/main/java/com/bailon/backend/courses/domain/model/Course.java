package com.bailon.backend.courses.domain.model;

import com.bailon.backend.users.domain.model.User;
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
public class Course {
    private UUID id;
    private String name;
    private String description;
    private String slug;
    private User user;
    private boolean enabled;
    private LocalDateTime createdAt;
}
