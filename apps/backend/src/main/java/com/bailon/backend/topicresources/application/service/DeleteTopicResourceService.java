package com.bailon.backend.topicresources.application.service;

import com.bailon.backend.topicresources.application.port.TopicResourceRepository;
import com.bailon.backend.topicresources.application.usecase.DeleteTopicResourceUseCase;
import com.bailon.backend.topicresources.domain.event.TopicResourcesDeletedEvent;
import com.bailon.backend.topicresources.domain.exception.TopicResourceNotFoundException;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class DeleteTopicResourceService implements DeleteTopicResourceUseCase {
    private final TopicResourceRepository repository;
    private final ApplicationEventPublisher event;

    public DeleteTopicResourceService(TopicResourceRepository repository, ApplicationEventPublisher event) {
        this.repository = repository;
        this.event = event;
    }

    @Override
    @Transactional
    public void execute(UUID teacherId, UUID resourceId) {
        if (!repository.existsByOwnerId(resourceId, teacherId)) {
            throw new TopicResourceNotFoundException("No se encontró el recurso");
        }
        repository.delete(resourceId);
        event.publishEvent(new TopicResourcesDeletedEvent(resourceId));
    }
}
