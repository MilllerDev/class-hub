package com.bailon.backend.chat.infrastructure.mapper;

import com.bailon.backend.chat.domain.model.ChatMessage;
import com.bailon.backend.chat.infrastructure.persistence.ChatMessageEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ChatMessageMapper {
    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "topicId", source = "topic.id")
    ChatMessage toDomain(ChatMessageEntity entity);

    @Mapping(target = "user.id", source = "userId")
    @Mapping(target = "topic.id", source = "topicId")
    ChatMessageEntity toEntity(ChatMessage model);
}