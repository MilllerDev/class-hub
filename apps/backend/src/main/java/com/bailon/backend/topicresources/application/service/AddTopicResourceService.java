package com.bailon.backend.topicresources.application.service;

import com.bailon.backend.topicresources.application.port.TopicResourceRepository;
import com.bailon.backend.topicresources.application.usecase.AddTopicResourceUseCase;
import com.bailon.backend.topicresources.domain.exception.InvalidResourceUrlException;
import com.bailon.backend.topicresources.domain.model.TopicResource;
import com.bailon.backend.topics.application.port.TopicRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class AddTopicResourceService implements AddTopicResourceUseCase {
    private final TopicResourceRepository repository;
    private final TopicRepository topicRepository;
    private final TopicResourceProcessor processor;

    public AddTopicResourceService(TopicResourceRepository repository, TopicRepository topicRepository, TopicResourceProcessor processor) {
        this.repository = repository;
        this.topicRepository = topicRepository;
        this.processor = processor;
    }

    @Override
    public TopicResource execute(UUID userId, UUID topicId, TopicResource resource) {
        if (topicRepository.existsTopicOwnsTeacher(topicId, userId)) {
            if (!resource.getFileType().matches(resource.getFileName())) {
                throw new InvalidResourceUrlException("El nombre del archivo no coincide con el tipo declarado");
            }
            resource.makeSaving(topicId);
            TopicResource saved = repository.save(resource);
            processor.process(saved, userId);
            return saved;
        }
        throw new RuntimeException("No tienes permisos sobre este tema");
    }
}
