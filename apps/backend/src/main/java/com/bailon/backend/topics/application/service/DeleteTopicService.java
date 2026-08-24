package com.bailon.backend.topics.application.service;

import com.bailon.backend.topicresources.application.port.TopicResourceRepository;
import com.bailon.backend.topicresources.domain.event.TopicResourcesDeletedEvent;
import com.bailon.backend.topics.application.port.TopicRepository;
import com.bailon.backend.topics.application.usecase.DeleteTopicUseCase;
import com.bailon.backend.topics.domain.exception.CourseAccessDeniedException;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class DeleteTopicService implements DeleteTopicUseCase {
    private final TopicRepository topicRepository;
    private final TopicResourceRepository topicResourceRepository;
    private final ApplicationEventPublisher eventPublisher;

    public DeleteTopicService(
            TopicRepository topicRepository,
            TopicResourceRepository topicResourceRepository,
            ApplicationEventPublisher eventPublisher
    ) {
        this.topicRepository = topicRepository;
        this.topicResourceRepository = topicResourceRepository;
        this.eventPublisher = eventPublisher;
    }

    @Override
    @Transactional
    public void execute(UUID teacherId, UUID id) {
        if (!topicRepository.existsTopicOwnsTeacher(id, teacherId)) {
            throw new CourseAccessDeniedException("No tienes permisos sobre este tema");
        }
        List<UUID> resourceIds = topicResourceRepository.findIdsByTopicId(id);
        topicRepository.delete(id);
        eventPublisher.publishEvent(new TopicResourcesDeletedEvent(resourceIds));
    }
}
