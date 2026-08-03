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

    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "user", ignore = true)
    CourseEntity toEntity(Course course);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "slug", ignore = true)
    @Mapping(target = "enabled", ignore = true)
    Course toDomain(CourseRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "slug", ignore = true)
    @Mapping(target = "enabled", ignore = true)
    Course toDomain(PartialCourseRequest request);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void toDomain(Course course, @MappingTarget Course update);
}
