package com.bailon.backend.topicresources.application.service;

import com.bailon.backend.topicresources.application.port.TopicResourceRepository;
import com.bailon.backend.topicresources.application.usecase.ReprocessTopicResourceUseCase;
import com.bailon.backend.topicresources.domain.exception.InvalidResourceStateException;
import com.bailon.backend.topicresources.domain.exception.TopicResourceNotFoundException;
import com.bailon.backend.topicresources.domain.model.ProcessingStatus;
import com.bailon.backend.topicresources.domain.model.TopicResource;
import com.bailon.backend.topics.application.usecase.GetTopicsUseCase;
import com.bailon.backend.users.domain.model.UserRole;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ReprocessTopicResourceService implements ReprocessTopicResourceUseCase {
    private final GetTopicsUseCase getTopics;
    private final TopicResourceRepository repository;
    private final TopicResourceProcessor processor;

    public ReprocessTopicResourceService(
            GetTopicsUseCase getTopics,
            TopicResourceRepository repository,
            TopicResourceProcessor processor
    ) {
        this.getTopics = getTopics;
        this.repository = repository;
        this.processor = processor;
    }

    @Override
    public TopicResource execute(UUID teacherId, UUID topicId, UUID resourceId) {
        getTopics.one(topicId, teacherId, UserRole.TEACHER);
        TopicResource resource = repository.findByIdAndTopicId(resourceId, topicId).orElseThrow(
                () -> new TopicResourceNotFoundException("No se encontró el recurso")
        );
        if (resource.getProcessingStatus() != ProcessingStatus.FAILED) {
            throw new InvalidResourceStateException("Solo se pueden reprocesar recursos que fallaron");
        }
        resource.setProcessingStatus(ProcessingStatus.PENDING);
        TopicResource saved = repository.save(resource);
        processor.process(saved, teacherId);
        return saved;
    }
}
