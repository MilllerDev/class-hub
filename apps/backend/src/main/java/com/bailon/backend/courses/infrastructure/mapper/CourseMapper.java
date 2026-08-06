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

    @Mapping(target = "user.id", source = "userId")
    CourseEntity toEntity(Course course);

    @BeanMapping(unmappedTargetPolicy = ReportingPolicy.IGNORE)
    Course toDomain(CourseRequest request);

    @BeanMapping(unmappedTargetPolicy = ReportingPolicy.IGNORE)
    Course toDomain(PartialCourseRequest request);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void toDomain(Course course, @MappingTarget Course update);
}
