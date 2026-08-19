package com.bailon.backend.topics.application.service;

import com.bailon.backend.courses.application.port.CourseRepository;
import com.bailon.backend.topics.application.port.TopicRepository;
import com.bailon.backend.topics.application.usecase.CreateTopicUseCase;
import com.bailon.backend.topics.domain.exception.CourseAccessDeniedException;
import com.bailon.backend.topics.domain.model.Topic;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class CreateTopicService implements CreateTopicUseCase {
    private final TopicRepository topicRepository;
    private final CourseRepository courseRepository;

    public CreateTopicService(TopicRepository topicRepository, CourseRepository courseRepository) {
        this.topicRepository = topicRepository;
        this.courseRepository = courseRepository;
    }

    @Override
    @Transactional
    public Topic execute(UUID teacherId, Topic topic) {
        if (!courseRepository.teacherOwnsCourse(topic.getCourseId(), teacherId)) {
            throw new CourseAccessDeniedException("No tienes permisos sobre este curso");
        }
        return topicRepository.save(topic);
    }
}