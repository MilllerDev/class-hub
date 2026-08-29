package com.bailon.backend.profile.infrastructure.mapper;

import com.bailon.backend.profile.domain.model.Profile;
import com.bailon.backend.profile.infrastructure.persistence.ProfileEntity;
import com.bailon.backend.profile.infrastructure.rest.request.PartialProfileRequest;
import com.bailon.backend.profile.infrastructure.rest.response.ProfileResponse;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface ProfileMapper {
    Profile toDomain(ProfileEntity entity);

    ProfileEntity toEntity(Profile profile);

    ProfileResponse toDto(Profile profile);

    @BeanMapping(unmappedTargetPolicy = ReportingPolicy.IGNORE, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void toDomain(PartialProfileRequest partial, @MappingTarget Profile update);
}
