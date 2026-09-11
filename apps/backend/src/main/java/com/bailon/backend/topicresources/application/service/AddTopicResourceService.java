package com.bailon.backend.topicresources.application.service;

import com.bailon.backend.topicresources.application.port.PgVectorResourcePort;
import com.bailon.backend.topicresources.application.port.TopicResourceRepository;
import com.bailon.backend.topicresources.application.usecase.AddTopicResourceUseCase;
import com.bailon.backend.topicresources.domain.exception.InvalidResourceFileTypeException;
import com.bailon.backend.topicresources.domain.exception.TopicNotOwnedByTeacherException;
import com.bailon.backend.topicresources.domain.model.TopicResource;
import com.bailon.backend.topics.application.port.TopicRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class AddTopicResourceService implements AddTopicResourceUseCase {
    private final TopicResourceRepository repository;
    private final TopicRepository topicRepository;
    private final PgVectorResourcePort pgVectorResourcePort;

    public AddTopicResourceService(TopicResourceRepository repository, TopicRepository topicRepository, PgVectorResourcePort pgVectorResourcePort) {
        this.repository = repository;
        this.topicRepository = topicRepository;
        this.pgVectorResourcePort = pgVectorResourcePort;
    }

    @Override
    public TopicResource execute(UUID userId, TopicResource resource) {
        ensureTeacherOwnsTopic(resource.getTopicId(), userId);
        ensureFileNameMatchesType(resource);

        UUID courseId = topicRepository.findCourse(resource.getTopicId());
        resource.markSaving();
        TopicResource saved = repository.save(resource);
        pgVectorResourcePort.process(saved, courseId, userId);
        return saved;
    }

    private void ensureTeacherOwnsTopic(UUID topicId, UUID userId) {
        if (!topicRepository.existsTopicOwnsTeacher(topicId, userId)) {
            throw new TopicNotOwnedByTeacherException("No tienes permisos sobre este tema");
        }
    }

    private void ensureFileNameMatchesType(TopicResource resource) {
        if (!resource.getFileType().matches(resource.getFileName())) {
            throw new InvalidResourceFileTypeException("El nombre del archivo no coincide con el tipo declarado");
        }
    }
}
