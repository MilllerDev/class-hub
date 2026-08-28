package com.bailon.backend.courses.application.service;

import com.bailon.backend.courses.application.port.CourseRepository;
import com.bailon.backend.courses.application.usecase.DeleteCourseUseCase;
import com.bailon.backend.courses.domain.exception.CourseNotFoundException;
import com.bailon.backend.topicresources.application.port.TopicResourceRepository;
import com.bailon.backend.topicresources.domain.event.TopicResourcesDeletedEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class DeleteCourseService implements DeleteCourseUseCase {
    private final CourseRepository courseRepository;
    private final TopicResourceRepository topicResourceRepository;
    private final ApplicationEventPublisher eventPublisher;

    public DeleteCourseService(
            CourseRepository courseRepository,
            TopicResourceRepository topicResourceRepository,
            ApplicationEventPublisher eventPublisher
    ) {
        this.courseRepository = courseRepository;
        this.topicResourceRepository = topicResourceRepository;
        this.eventPublisher = eventPublisher;
    }

    @Override
    @Transactional
    public void execute(UUID id) {
        if (!courseRepository.existsById(id)) {
            throw new CourseNotFoundException("No se encontró el curso");
        }
        List<UUID> resourceIds = topicResourceRepository.findIdsByCourseId(id);
        courseRepository.delete(id);
        eventPublisher.publishEvent(new TopicResourcesDeletedEvent(resourceIds));
    }
}
