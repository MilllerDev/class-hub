package com.bailon.backend.users.infrastructure.mapper;

import com.bailon.backend.users.domain.model.User;
import com.bailon.backend.users.infrastructure.persistence.UserEntity;
import com.bailon.backend.users.infrastructure.rest.response.UserResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface
UserMapper {
    User toDomain(UserEntity entity);

    UserEntity toEntity(User user);

    UserResponse toDto(User user);
}
