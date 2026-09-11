package com.bailon.backend.topicresources.application.service;

import com.bailon.backend.topicresources.application.port.PgVectorResourcePort;
import com.bailon.backend.topicresources.application.port.TopicResourceRepository;
import com.bailon.backend.topicresources.application.usecase.ReprocessTopicResourceUseCase;
import com.bailon.backend.topicresources.domain.exception.InvalidResourceStateException;
import com.bailon.backend.topicresources.domain.exception.TopicResourceNotFoundException;
import com.bailon.backend.topicresources.domain.model.ResourceStatus;
import com.bailon.backend.topicresources.domain.model.TopicResource;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ReprocessTopicResourceService implements ReprocessTopicResourceUseCase {
    private final TopicResourceRepository repository;
    private final PgVectorResourcePort pgVectorResourcePort;

    public ReprocessTopicResourceService(TopicResourceRepository repository, PgVectorResourcePort pgVectorResourcePort) {
        this.repository = repository;
        this.pgVectorResourcePort = pgVectorResourcePort;
    }

    @Override
    public TopicResource execute(UUID teacherId, UUID resourceId) {
        TopicResource resource = repository.findByIdAndOwner(resourceId, teacherId).orElseThrow(
                () -> new TopicResourceNotFoundException("No se encontró el recurso")
        );
        ensureCanReprocess(resource);

        UUID courseId = repository.findCourseByResourceId(resourceId);
        resource.markSaving();
        TopicResource saved = repository.save(resource);
        pgVectorResourcePort.process(saved, courseId, teacherId);
        return saved;
    }

    private void ensureCanReprocess(TopicResource resource) {
        if (resource.getStatus() != ResourceStatus.FAILED) {
            throw new InvalidResourceStateException("Solo se pueden reprocesar recursos que fallaron");
        }
    }
}
