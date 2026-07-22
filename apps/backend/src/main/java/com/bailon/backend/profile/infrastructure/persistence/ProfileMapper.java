package com.bailon.backend.profile.infrastructure.persistence;

import com.bailon.backend.profile.domain.model.Profile;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProfileMapper {
    Profile toDomain(ProfileEntity entity);

    ProfileEntity toEntity(Profile profile);

    @Mapping(target = "user", ignore = true)
    @Mapping(target = "id", ignore = true)
    Profile toDomain(ProfileRequest request);
}
