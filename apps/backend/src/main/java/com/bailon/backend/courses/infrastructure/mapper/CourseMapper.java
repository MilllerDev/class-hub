package com.bailon.backend.courses.infrastructure.mapper;

import com.bailon.backend.courses.domain.model.Course;
import com.bailon.backend.courses.infrastructure.persistence.CourseEntity;
import com.bailon.backend.courses.infrastructure.rest.request.CourseRequest;
import com.bailon.backend.courses.infrastructure.rest.request.PartialCourseRequest;
import com.bailon.backend.users.domain.model.User;
import org.mapstruct.*;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CourseMapper {
    Course toDomain(CourseEntity entity);

    CourseEntity toEntity(Course course);

    Course toDomain(CourseRequest request);

    Course toDomain(PartialCourseRequest request);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void toDomain(Course course, @MappingTarget Course update);

    @AfterMapping
    default void setUserIfPresent(CourseRequest request, @MappingTarget Course course) {
        if (request.userId() != null) {
            User user = new User();
            user.setId(request.userId());
            course.setUser(user);
        }
    }
}
