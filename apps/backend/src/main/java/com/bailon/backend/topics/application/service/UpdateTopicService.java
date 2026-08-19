package com.bailon.backend.topics.application.service;

import com.bailon.backend.courses.domain.exception.CourseNotFoundException;
import com.bailon.backend.topics.application.port.TopicRepository;
import com.bailon.backend.topics.application.usecase.UpdateTopicUseCase;
import com.bailon.backend.topics.domain.exception.TopicNotFoundException;
import com.bailon.backend.topics.domain.model.Topic;
import com.bailon.backend.topics.infrastructure.mapper.TopicMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class UpdateTopicService implements UpdateTopicUseCase {
    private final TopicRepository topicRepository;
    private final TopicMapper mapper;

    public UpdateTopicService(TopicRepository topicRepository, TopicMapper mapper) {
        this.topicRepository = topicRepository;
        this.mapper = mapper;
    }

    @Override
    @Transactional
    public Topic execute(UUID teacherId, UUID id, Topic topic) {
        Topic found = topicRepository.findBelongTeacher(id, teacherId).orElseThrow(
                () -> new TopicNotFoundException("No se encontró el tema")
        );
        if (!topicRepository.courseOwnedByTeacher(found.getCourseId(), teacherId)) {
            throw new CourseNotFoundException("No se encontró el curso");
        }
        mapper.toDomain(topic, found);
        return topicRepository.save(found);
    }
}