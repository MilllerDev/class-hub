package com.bailon.backend.profile.domain.model;

import com.bailon.backend.users.domain.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Profile {
    private UUID id;
    private String name;
    private String lastName;
    private String avatarUrl;
    private User user;
}
