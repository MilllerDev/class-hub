package com.bailon.backend.topicresources.application.service;

import com.bailon.backend.topicresources.application.port.ResourceVectorStore;
import com.bailon.backend.topicresources.application.port.TopicResourceRepository;
import com.bailon.backend.topicresources.domain.model.TopicResource;
import org.springframework.ai.document.Document;
import org.springframework.ai.reader.tika.TikaDocumentReader;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.core.io.UrlResource;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class TopicResourceProcessor {
    private final TopicResourceRepository repository;
    private final ResourceVectorStore vectorStore;

    public TopicResourceProcessor(TopicResourceRepository repository, ResourceVectorStore vectorStore) {
        this.repository = repository;
        this.vectorStore = vectorStore;
    }

    @Async("aiService")
    public void process(TopicResource resource, UUID userId) {
        try {
            resource.markProcessing();
            repository.save(resource);
            UrlResource file = new UrlResource(resource.getFileUrl());

            List<Document> documents = new TikaDocumentReader(file).get();
            TokenTextSplitter splitter = TokenTextSplitter.builder()
                    .withChunkSize(800)
                    .withMinChunkSizeChars(350)
                    .withMinChunkLengthToEmbed(5)
                    .withMaxNumChunks(10000)
                    .withKeepSeparator(true)
                    .build();
            List<Document> chunks = splitter.split(documents);
            Map<String, Object> metadata = buildMetadata(resource, userId);
            chunks.forEach(chunk -> chunk.getMetadata().putAll(metadata));
            vectorStore.storeChunks(chunks);
            resource.markCompleted();
            repository.save(resource);
        } catch (Exception e) {
            resource.markFailed();
            repository.save(resource);
        }
    }

    private Map<String, Object> buildMetadata(TopicResource resource, UUID userId) {
        return Map.of(
                "topicResourceId", resource.getId().toString(),
                "topicId", resource.getTopicId().toString(),
                "userId", userId.toString(),
                "documentName", resource.getFileName()
        );
    }
}
