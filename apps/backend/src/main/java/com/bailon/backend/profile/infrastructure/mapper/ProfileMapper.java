package com.bailon.backend.profile.infrastructure.mapper;

import com.bailon.backend.profile.domain.model.Profile;
import com.bailon.backend.profile.infrastructure.persistence.ProfileEntity;
import com.bailon.backend.profile.infrastructure.rest.request.ProfileRequest;
import com.bailon.backend.profile.infrastructure.rest.response.ProfileResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProfileMapper {
    Profile toDomain(ProfileEntity entity);

    ProfileEntity toEntity(Profile profile);

    @Mapping(target = "user", ignore = true)
    @Mapping(target = "id", ignore = true)
    Profile toDomain(ProfileRequest request);

    ProfileResponse toDto(Profile profile);
}
