package com.bailon.backend.topicresources.infrastructure.mapper;

import com.bailon.backend.topicresources.domain.model.TopicResource;
import com.bailon.backend.topicresources.infrastructure.persistence.TopicResourceEntity;
import com.bailon.backend.topicresources.infrastructure.rest.request.TopicResourceRequest;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring")
public interface TopicResourceMapper {
    @Mapping(target = "topicId", source = "topic.id")
    TopicResource toDomain(TopicResourceEntity entity);

    @Mapping(target = "topic.id", source = "topicId")
    TopicResourceEntity toEntity(TopicResource resource);

    @BeanMapping(unmappedTargetPolicy = ReportingPolicy.IGNORE, unmappedSourcePolicy = ReportingPolicy.IGNORE)
    TopicResource toDomain(TopicResourceRequest request);
}
