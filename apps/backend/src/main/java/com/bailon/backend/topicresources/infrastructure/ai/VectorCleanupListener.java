package com.bailon.backend.topicresources.infrastructure.ai;

import com.bailon.backend.topicresources.application.port.ResourceVectorStore;
import com.bailon.backend.topicresources.domain.event.TopicResourcesDeletedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import java.util.UUID;

@Component
public class VectorCleanupListener {
    private static final Logger log = LoggerFactory.getLogger(VectorCleanupListener.class);

    private final ResourceVectorStore vectorStore;

    public VectorCleanupListener(ResourceVectorStore vectorStore) {
        this.vectorStore = vectorStore;
    }

    @Async("aiService")
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void onTopicResourcesDeleted(TopicResourcesDeletedEvent event) {
        for (UUID resourceId : event.resourceIds()) {
            try {
                vectorStore.deleteByTopicResourceId(resourceId);
            } catch (Exception e) {
                log.error("Error purgando los vectores del recurso {}: {}", resourceId, e.getMessage(), e);
            }
        }
    }
}
