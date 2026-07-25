package com.bailon.backend.token.infrastructure.mapper;

import com.bailon.backend.token.domain.model.ActivationToken;
import com.bailon.backend.token.domain.model.Token;
import com.bailon.backend.token.infrastructure.entity.ActivationTokenEntity;
import com.bailon.backend.token.infrastructure.entity.TokenEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TokenMapper {
    @Mapping(target = "userId", source = "user.id")
    Token toDomain(TokenEntity entity);

    @Mapping(target = "user.id", source = "userId")
    TokenEntity toEntity(Token token);

    @Mapping(target = "user.id", source = "userId")
    @Mapping(target = "createdAt", ignore = true)
    ActivationTokenEntity toEntity(ActivationToken activationToken);

    @Mapping(target = "userId", source = "user.id")
    ActivationToken toDomain(ActivationTokenEntity entity);
}
