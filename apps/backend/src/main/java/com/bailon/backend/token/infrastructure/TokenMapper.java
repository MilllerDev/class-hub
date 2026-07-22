package com.bailon.backend.token.infrastructure;

import com.bailon.backend.token.domain.model.Token;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TokenMapper {
    @Mapping(target = "userId", source = "user.id")
    Token toDomain(TokenEntity entity);

    @Mapping(target = "user.id", source = "userId")
    TokenEntity toEntity(Token token);
}
