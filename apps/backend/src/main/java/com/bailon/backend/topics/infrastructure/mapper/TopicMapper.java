package com.bailon.backend.topics.infrastructure.mapper;

import com.bailon.backend.topics.domain.model.Topic;
import com.bailon.backend.topics.infrastructure.persistence.TopicEntity;
import com.bailon.backend.topics.infrastructure.rest.request.PartialTopicRequest;
import com.bailon.backend.topics.infrastructure.rest.request.TopicRequest;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface TopicMapper {
    @Mapping(target = "courseId", source = "course.id")
    Topic toDomain(TopicEntity entity);

    @Mapping(target = "course.id", source = "courseId")
    TopicEntity toEntity(Topic topic);

    @BeanMapping(unmappedTargetPolicy = ReportingPolicy.IGNORE, unmappedSourcePolicy = ReportingPolicy.IGNORE)
    Topic toDomain(TopicRequest request);

    @BeanMapping(unmappedTargetPolicy = ReportingPolicy.IGNORE, unmappedSourcePolicy = ReportingPolicy.IGNORE)
    Topic toDomain(PartialTopicRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void toDomain(Topic topic, @MappingTarget Topic update);
}