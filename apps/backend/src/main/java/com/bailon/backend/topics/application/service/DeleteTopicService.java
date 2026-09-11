package com.bailon.backend.topics.application.service;

import com.bailon.backend.topics.application.port.TopicRepository;
import com.bailon.backend.topics.application.usecase.DeleteTopicUseCase;
import com.bailon.backend.topics.domain.event.TopicDeletedEvent;
import com.bailon.backend.topics.domain.exception.CourseAccessDeniedException;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class DeleteTopicService implements DeleteTopicUseCase {
    private final TopicRepository topicRepository;
    private final ApplicationEventPublisher event;

    public DeleteTopicService(TopicRepository topicRepository, ApplicationEventPublisher event) {
        this.topicRepository = topicRepository;
        this.event = event;
    }

    @Override
    @Transactional
    public void execute(UUID teacherId, UUID id) {
        if (!topicRepository.existsTopicOwnsTeacher(id, teacherId)) {
            throw new CourseAccessDeniedException("No tienes permisos sobre este tema");
        }
        topicRepository.delete(id);
        event.publishEvent(new TopicDeletedEvent(id));
    }
}
