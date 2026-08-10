package com.bailon.backend.courses.infrastructure.mapper;

import com.bailon.backend.courses.domain.model.Course;
import com.bailon.backend.courses.infrastructure.persistence.CourseEntity;
import com.bailon.backend.courses.infrastructure.rest.request.CourseRequest;
import com.bailon.backend.courses.infrastructure.rest.request.PartialCourseRequest;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface CourseMapper {
    @Mapping(target = "userId", source = "user.id")
    Course toDomain(CourseEntity entity);

    @Mapping(target = "user", ignore = true)
    CourseEntity toEntity(Course course);

    @BeanMapping(unmappedTargetPolicy = ReportingPolicy.IGNORE, unmappedSourcePolicy = ReportingPolicy.IGNORE)
    Course toDomain(CourseRequest request);

    @BeanMapping(unmappedTargetPolicy = ReportingPolicy.IGNORE, unmappedSourcePolicy = ReportingPolicy.IGNORE)
    Course toDomain(PartialCourseRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "slug", ignore = true)
    @Mapping(target = "enabled", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void toDomain(Course course, @MappingTarget Course update);
}
