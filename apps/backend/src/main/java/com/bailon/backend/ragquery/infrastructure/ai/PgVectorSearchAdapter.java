package com.bailon.backend.ragquery.infrastructure.ai;

import com.bailon.backend.ragquery.application.port.ResourceVectorSearchPort;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.ai.vectorstore.filter.Filter.Expression;
import org.springframework.ai.vectorstore.filter.FilterExpressionBuilder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class PgVectorSearchAdapter implements ResourceVectorSearchPort {
    private static final int TOP_K = 6;
    private static final String TOPIC_ID_METADATA = "topicId";
    private static final String TOPIC_RESOURCE_ID_METADATA = "topicResourceId";
    private final VectorStore vectorStore;

    public PgVectorSearchAdapter(VectorStore vectorStore) {
        this.vectorStore = vectorStore;
    }

    @Override
    public List<String> searchContext(String query, UUID topicId, List<UUID> topicResourceIds) {
        if (topicResourceIds == null || topicResourceIds.isEmpty()) {
            throw new IllegalArgumentException("Es necesario enviar los documentos");
        }
        SearchRequest request = SearchRequest.builder()
                .query(query)
                .topK(TOP_K)
                .filterExpression(buildFilter(topicId, topicResourceIds))
                .build();
        return vectorStore.similaritySearch(request).stream()
                .map(Document::getText)
                .toList();
    }

    private Expression buildFilter(UUID topicId, List<UUID> topicResourceIds) {
        FilterExpressionBuilder builder = new FilterExpressionBuilder();
        FilterExpressionBuilder.Op topicFilter = builder.eq(TOPIC_ID_METADATA, topicId.toString());
        return builder.and(
                topicFilter,
                builder.in(TOPIC_RESOURCE_ID_METADATA, topicResourceIds.toArray())
        ).build();
    }
}
