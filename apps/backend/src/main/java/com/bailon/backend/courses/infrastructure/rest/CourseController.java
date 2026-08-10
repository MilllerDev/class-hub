package com.bailon.backend.courses.infrastructure.rest;

import com.bailon.backend.config.ApiResponse;
import com.bailon.backend.courses.application.usecase.CreateCourseUseCase;
import com.bailon.backend.courses.application.usecase.DeleteCourseUseCase;
import com.bailon.backend.courses.application.usecase.GetCourseUseCase;
import com.bailon.backend.courses.application.usecase.UpdateCourseUseCase;
import com.bailon.backend.courses.domain.model.Course;
import com.bailon.backend.courses.infrastructure.mapper.CourseMapper;
import com.bailon.backend.courses.infrastructure.rest.request.CourseRequest;
import com.bailon.backend.courses.infrastructure.rest.request.PartialCourseRequest;
import com.bailon.backend.users.infrastructure.security.AppUserPrincipal;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/courses")
@RequiredArgsConstructor
public class CourseController {
    private final GetCourseUseCase getCourse;
    private final CreateCourseUseCase createCourse;
    private final DeleteCourseUseCase deleteCourse;
    private final UpdateCourseUseCase updateCourse;
    private final CourseMapper mapper;

    @GetMapping("/{slug}")
    public ResponseEntity<ApiResponse<Course>> findBySlug(
            @PathVariable String slug,
            @AuthenticationPrincipal AppUserPrincipal auth
    ) {
        return ResponseEntity.ok(
                ApiResponse.success("Curso encontrado", getCourse.execute(slug, auth.user().getRole()))
        );
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ApiResponse<Course>> create(@Valid @RequestBody CourseRequest request) {
        Course response = createCourse.execute(mapper.toDomain(request), request.useAi());
        return ResponseEntity.ok(
                ApiResponse.success("Curso: " + request.name() + " creado exitosamente", response)
        );
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ApiResponse<Course>> update(@PathVariable UUID id, @Valid @RequestBody PartialCourseRequest request) {
        Course course = mapper.toDomain(request);
        return ResponseEntity.ok(
                ApiResponse.success("Curso actualizado", updateCourse.execute(id, course, request.useAi()))
        );
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable UUID id) {
        deleteCourse.execute(id);
        return ResponseEntity.ok(ApiResponse.success("Curso eliminado exitosamente", null));
    }
}
