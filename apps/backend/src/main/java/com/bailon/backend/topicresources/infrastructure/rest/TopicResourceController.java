package com.bailon.backend.topicresources.infrastructure.rest;

import com.bailon.backend.config.ApiResponse;
import com.bailon.backend.topicresources.application.usecase.AddTopicResourceUseCase;
import com.bailon.backend.topicresources.application.usecase.DeleteTopicResourceUseCase;
import com.bailon.backend.topicresources.application.usecase.GetTopicResourcesUseCase;
import com.bailon.backend.topicresources.application.usecase.ReprocessTopicResourceUseCase;
import com.bailon.backend.topicresources.domain.model.TopicResource;
import com.bailon.backend.topicresources.infrastructure.mapper.TopicResourceMapper;
import com.bailon.backend.topicresources.infrastructure.rest.request.TopicResourceRequest;
import com.bailon.backend.users.infrastructure.security.AppUserPrincipal;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/topic-resources")
@RequiredArgsConstructor
public class TopicResourceController {
    private final AddTopicResourceUseCase addResource;
    private final GetTopicResourcesUseCase getResources;
    private final DeleteTopicResourceUseCase deleteResource;
    private final ReprocessTopicResourceUseCase reprocessResource;
    private final TopicResourceMapper mapper;

    @PostMapping
    @PreAuthorize("hasAuthority('TEACHER')")
    public ResponseEntity<ApiResponse<TopicResource>> add(
            @AuthenticationPrincipal AppUserPrincipal auth, @Valid @RequestBody TopicResourceRequest req
    ) {
        TopicResource resource = addResource.execute(auth.user().getId(), mapper.toDomain(req));
        return ResponseEntity.status(HttpStatus.CREATED).body(
                ApiResponse.success("Recurso agregado.", resource)
        );
    }

    @GetMapping("/topic/{topicId}")
    public ResponseEntity<ApiResponse<List<TopicResource>>> list(
            @AuthenticationPrincipal AppUserPrincipal auth, @PathVariable UUID topicId
    ) {
        List<TopicResource> resources = getResources.list(topicId, auth.user().getId(), auth.user().getRole());
        return ResponseEntity.ok(
                ApiResponse.success("Recursos del tema encontrados", resources)
        );
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('TEACHER')")
    public ResponseEntity<ApiResponse<Void>> delete(
            @AuthenticationPrincipal AppUserPrincipal auth, @PathVariable UUID id
    ) {
        deleteResource.execute(auth.user().getId(), id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(
                ApiResponse.success("Recurso eliminado exitosamente", null)
        );
    }

    @PostMapping("/{id}/reprocess")
    @PreAuthorize("hasAuthority('TEACHER')")
    public ResponseEntity<ApiResponse<TopicResource>> reprocess(
            @AuthenticationPrincipal AppUserPrincipal auth, @PathVariable UUID id
    ) {
        TopicResource resource = reprocessResource.execute(auth.user().getId(), id);
        return ResponseEntity.ok(ApiResponse.success("Reprocesamiento del recurso iniciado", resource));
    }
}
