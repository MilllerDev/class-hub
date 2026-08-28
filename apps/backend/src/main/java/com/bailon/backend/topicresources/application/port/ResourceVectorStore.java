package com.bailon.backend.topicresources.application.port;

import org.springframework.ai.document.Document;

import java.util.List;
import java.util.UUID;

public interface ResourceVectorStore {
    void storeChunks(List<Document> chunks);

    void deleteByTopicResourceId(UUID topicResourceId);
}
