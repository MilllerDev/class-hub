package com.bailon.backend.courses.application.service;

import com.bailon.backend.courses.application.port.CourseRepository;
import com.bailon.backend.courses.application.usecase.DeleteCourseUseCase;
import com.bailon.backend.courses.domain.event.CourseDeletedEvent;
import com.bailon.backend.courses.domain.exception.CourseNotFoundException;
import com.bailon.backend.topics.application.port.TopicRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DeleteCourseService implements DeleteCourseUseCase {
    private final CourseRepository courseRepository;
    private final TopicRepository topicRepository;
    private final ApplicationEventPublisher eventPublisher;

    @Override
    @Transactional
    public void execute(UUID id) {
        if (!courseRepository.existsById(id)) {
            throw new CourseNotFoundException("No se encontró el curso");
        }
        courseRepository.delete(id);
        eventPublisher.publishEvent(new CourseDeletedEvent(id));
    }
}
