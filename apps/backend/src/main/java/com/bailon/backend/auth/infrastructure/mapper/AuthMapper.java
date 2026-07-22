package com.bailon.backend.auth.infrastructure.mapper;

import com.bailon.backend.auth.infrastructure.rest.request.RegisterRequest;
import com.bailon.backend.profile.domain.model.Profile;
import com.bailon.backend.users.domain.model.User;
import com.bailon.backend.users.infrastructure.persistence.UserResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AuthMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "avatarUrl", ignore = true)
    Profile toDomain(RegisterRequest request);

    UserResponse toDto(User user);
}
