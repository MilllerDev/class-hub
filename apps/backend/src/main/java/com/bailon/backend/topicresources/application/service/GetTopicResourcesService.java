package com.bailon.backend.topicresources.application.service;

import com.bailon.backend.topicresources.application.port.TopicResourceRepository;
import com.bailon.backend.topicresources.application.usecase.GetTopicResourcesUseCase;
import com.bailon.backend.topicresources.domain.model.TopicResource;
import com.bailon.backend.users.domain.model.UserRole;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class GetTopicResourcesService implements GetTopicResourcesUseCase {
    private final TopicResourceRepository repository;

    public GetTopicResourcesService(TopicResourceRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<TopicResource> list(UUID topicId, UUID userId, UserRole role) {
        return switch (role) {
            case ADMIN, STUDENT -> repository.findByTopic(topicId);
            case TEACHER -> repository.findResourcesOwner(topicId, userId);
        };
    }
}