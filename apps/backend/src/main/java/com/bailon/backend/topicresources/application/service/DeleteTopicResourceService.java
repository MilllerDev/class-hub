package com.bailon.backend.topicresources.application.service;

import com.bailon.backend.topicresources.application.port.TopicResourceRepository;
import com.bailon.backend.topicresources.application.usecase.DeleteTopicResourceUseCase;
import com.bailon.backend.topicresources.domain.event.TopicResourcesDeletedEvent;
import com.bailon.backend.topicresources.domain.exception.TopicResourceNotFoundException;
import com.bailon.backend.topicresources.domain.model.TopicResource;
import com.bailon.backend.topics.application.usecase.GetTopicsUseCase;
import com.bailon.backend.users.domain.model.UserRole;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class DeleteTopicResourceService implements DeleteTopicResourceUseCase {
    private final GetTopicsUseCase getTopics;
    private final TopicResourceRepository repository;
    private final ApplicationEventPublisher eventPublisher;

    public DeleteTopicResourceService(
            GetTopicsUseCase getTopics,
            TopicResourceRepository repository,
            ApplicationEventPublisher eventPublisher
    ) {
        this.getTopics = getTopics;
        this.repository = repository;
        this.eventPublisher = eventPublisher;
    }

    @Override
    @Transactional
    public void execute(UUID teacherId, UUID topicId, UUID resourceId) {
        getTopics.one(topicId, teacherId, UserRole.TEACHER);
        TopicResource resource = repository.findByIdAndTopicId(resourceId, topicId).orElseThrow(
                () -> new TopicResourceNotFoundException("No se encontró el recurso")
        );
        repository.delete(resource.getId());
        eventPublisher.publishEvent(new TopicResourcesDeletedEvent(List.of(resource.getId())));
    }
}
