package com.bailon.backend.topicresources.application.service;

import com.bailon.backend.topicresources.application.port.TopicResourceRepository;
import com.bailon.backend.topicresources.application.usecase.GetTopicResourcesUseCase;
import com.bailon.backend.topicresources.domain.exception.TopicResourceNotFoundException;
import com.bailon.backend.topicresources.domain.model.TopicResource;
import com.bailon.backend.topics.application.usecase.GetTopicsUseCase;
import com.bailon.backend.users.domain.model.UserRole;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class GetTopicResourcesService implements GetTopicResourcesUseCase {
    private final GetTopicsUseCase getTopics;
    private final TopicResourceRepository repository;

    public GetTopicResourcesService(GetTopicsUseCase getTopics, TopicResourceRepository repository) {
        this.getTopics = getTopics;
        this.repository = repository;
    }

    @Override
    public List<TopicResource> list(UUID topicId, UUID userId, UserRole role) {
        getTopics.one(topicId, userId, role);
        return repository.findByTopicId(topicId);
    }

    @Override
    public TopicResource one(UUID topicId, UUID resourceId, UUID userId, UserRole role) {
        getTopics.one(topicId, userId, role);
        return repository.findByIdAndTopicId(resourceId, topicId).orElseThrow(
                () -> new TopicResourceNotFoundException("No se encontró el recurso")
        );
    }
}
