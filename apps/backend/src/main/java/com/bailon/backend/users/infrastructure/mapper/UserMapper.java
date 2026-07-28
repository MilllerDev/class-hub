package com.bailon.backend.users.infrastructure.mapper;

import com.bailon.backend.users.domain.model.User;
import com.bailon.backend.users.infrastructure.persistence.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(target = "authorities", ignore = true)
    User toDomain(UserEntity entity);

    @Mapping(target = "createdAt", ignore = true)
    UserEntity toEntity(User user);
}
