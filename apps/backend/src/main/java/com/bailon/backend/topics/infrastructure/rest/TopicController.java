package com.bailon.backend.topics.infrastructure.rest;

import com.bailon.backend.config.ApiResponse;
import com.bailon.backend.topics.application.usecase.CreateTopicUseCase;
import com.bailon.backend.topics.application.usecase.DeleteTopicUseCase;
import com.bailon.backend.topics.application.usecase.GetTopicsUseCase;
import com.bailon.backend.topics.application.usecase.UpdateTopicUseCase;
import com.bailon.backend.topics.domain.model.Topic;
import com.bailon.backend.topics.domain.model.TopicOrder;
import com.bailon.backend.topics.infrastructure.mapper.TopicMapper;
import com.bailon.backend.topics.infrastructure.rest.request.PartialTopicRequest;
import com.bailon.backend.topics.infrastructure.rest.request.TopicRequest;
import com.bailon.backend.users.infrastructure.security.AppUserPrincipal;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/topics")
@RequiredArgsConstructor
public class TopicController {
    private final GetTopicsUseCase getTopics;
    private final CreateTopicUseCase createTopic;
    private final UpdateTopicUseCase updateTopic;
    private final DeleteTopicUseCase deleteTopic;
    private final TopicMapper mapper;

    @GetMapping("/course/{slug}")
    public ResponseEntity<ApiResponse<List<Topic>>> findByCourse(
            @PathVariable String slug,
            @RequestParam(defaultValue = "OLDEST") TopicOrder sort,
            @AuthenticationPrincipal AppUserPrincipal auth
    ) {
        List<Topic> topics = getTopics.list(slug, auth.user().getId(), auth.user().getRole(), sort);
        return ResponseEntity.ok(ApiResponse.success("Temas del curso encontrados", topics));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Topic>> findById(
            @PathVariable UUID id,
            @AuthenticationPrincipal AppUserPrincipal auth
    ) {
        Topic topic = getTopics.one(id, auth.user().getId(), auth.user().getRole());
        return ResponseEntity.ok(ApiResponse.success("Tema encontrado", topic));
    }

    @PostMapping
    @PreAuthorize("hasAuthority('TEACHER')")
    public ResponseEntity<ApiResponse<Topic>> create(
            @AuthenticationPrincipal AppUserPrincipal auth,
            @Valid @RequestBody TopicRequest req
    ) {
        Topic topic = createTopic.execute(auth.user().getId(), mapper.toDomain(req));
        return ResponseEntity.ok(
                ApiResponse.success("Tema: " + req.title() + " creado exitosamente", topic)
        );
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasAuthority('TEACHER')")
    public ResponseEntity<ApiResponse<Topic>> update(
            @AuthenticationPrincipal AppUserPrincipal auth,
            @PathVariable UUID id,
            @Valid @RequestBody PartialTopicRequest req
    ) {
        Topic topic = updateTopic.execute(auth.user().getId(), id, mapper.toDomain(req));
        return ResponseEntity.ok(ApiResponse.success("Tema actualizado", topic));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('TEACHER')")
    public ResponseEntity<ApiResponse<Void>> delete(
            @AuthenticationPrincipal AppUserPrincipal auth,
            @PathVariable UUID id
    ) {
        deleteTopic.execute(auth.user().getId(), id);
        return ResponseEntity.ok(ApiResponse.success("Tema eliminado exitosamente", null));
    }
}