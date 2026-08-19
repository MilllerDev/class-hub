package com.bailon.backend.topics.application.service;

import com.bailon.backend.topics.application.port.TopicRepository;
import com.bailon.backend.topics.application.usecase.GetTopicsUseCase;
import com.bailon.backend.topics.domain.exception.TopicNotFoundException;
import com.bailon.backend.topics.domain.model.Topic;
import com.bailon.backend.topics.domain.model.TopicOrder;
import com.bailon.backend.users.domain.model.UserRole;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class GetTopicsService implements GetTopicsUseCase {
    private final TopicRepository topicRepository;

    public GetTopicsService(TopicRepository topicRepository) {
        this.topicRepository = topicRepository;
    }

    @Override
    public Topic one(UUID id, UUID userId, UserRole role) {
        return switch (role) {
            case ADMIN, STUDENT -> topicRepository.findById(id).orElseThrow(
                    () -> new TopicNotFoundException("No se encontró el tema")
            );
            case TEACHER -> topicRepository.findBelongTeacher(id, userId).orElseThrow(
                    () -> new TopicNotFoundException("No se encontró el tema")
            );
        };
    }

    @Override
    public List<Topic> list(String slug, UUID userId, UserRole role, TopicOrder order) {
        return switch (role) {
            case ADMIN, STUDENT -> topicRepository.findByCourseSlug(slug, order);
            case TEACHER -> topicRepository.findAllBelongTeacher(slug, userId, order);
        };
    }
}