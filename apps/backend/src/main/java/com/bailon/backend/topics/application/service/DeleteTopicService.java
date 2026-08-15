package com.bailon.backend.topics.application.service;

import com.bailon.backend.topics.application.port.TopicRepository;
import com.bailon.backend.topics.application.usecase.DeleteTopicUseCase;
import com.bailon.backend.topics.domain.exception.CourseAccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class DeleteTopicService implements DeleteTopicUseCase {
    private final TopicRepository topicRepository;

    public DeleteTopicService(TopicRepository topicRepository) {
        this.topicRepository = topicRepository;
    }

    @Override
    public void execute(UUID teacherId, UUID id) {
        if (!topicRepository.existsTopicOwnsTeacher(id, teacherId)) {
            throw new CourseAccessDeniedException("No tienes permisos sobre este tema");
        }
        topicRepository.delete(id);
    }
}