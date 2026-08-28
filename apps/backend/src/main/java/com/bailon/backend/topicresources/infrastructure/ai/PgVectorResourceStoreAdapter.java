package com.bailon.backend.topicresources.infrastructure.ai;

import com.bailon.backend.topicresources.application.port.ResourceVectorStore;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.ai.vectorstore.filter.Filter.Expression;
import org.springframework.ai.vectorstore.filter.FilterExpressionBuilder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class PgVectorResourceStoreAdapter implements ResourceVectorStore {
    private final VectorStore vectorStore;

    public PgVectorResourceStoreAdapter(VectorStore vectorStore) {
        this.vectorStore = vectorStore;
    }

    @Override
    public void storeChunks(List<Document> chunks) {
        vectorStore.add(chunks);
    }

    @Override
    public void deleteByTopicResourceId(UUID topicResourceId) {
        FilterExpressionBuilder filter = new FilterExpressionBuilder();
        Expression expression = filter.eq("topicResourceId", topicResourceId).build();
        vectorStore.delete(expression);
    }
}
