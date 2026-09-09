package com.bailon.backend.topicresources.infrastructure.ai;

import com.bailon.backend.topicresources.application.port.PgVectorResourcePort;
import com.bailon.backend.topicresources.application.port.TopicResourceRepository;
import com.bailon.backend.topicresources.domain.model.TopicResource;
import org.springframework.ai.document.Document;
import org.springframework.ai.reader.tika.TikaDocumentReader;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.ai.vectorstore.filter.Filter.Expression;
import org.springframework.ai.vectorstore.filter.FilterExpressionBuilder;
import org.springframework.core.io.UrlResource;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Component
public class PgVectorResourceAdapter implements PgVectorResourcePort {
    private final TopicResourceRepository resourceRepository;
    private final VectorStore vectorStore;

    public PgVectorResourceAdapter(TopicResourceRepository resourceRepository, VectorStore vectorStore) {
        this.resourceRepository = resourceRepository;
        this.vectorStore = vectorStore;
    }

    @Override
    public List<String> searchContext(String query, UUID topicId, List<UUID> topicResourceIds) {
        if (topicResourceIds == null || topicResourceIds.isEmpty()) {
            throw new IllegalArgumentException("Es necesario enviar el documento");
        }
        SearchRequest request = SearchRequest.builder()
                .query(query)
                .topK(3)
                .filterExpression(buildFilter(topicId, topicResourceIds))
                .build();
        return vectorStore.similaritySearch(request).stream().map(Document::getText).toList();
    }

    @Async("aiService")
    @Override
    public void process(TopicResource resource, UUID courseId, UUID userId) {
        resource.markProcessing();
        resourceRepository.save(resource);
        try {
            UrlResource url = new UrlResource(resource.getFileUrl());

            List<Document> documents = new TikaDocumentReader(url).get();
            TokenTextSplitter splitter = TokenTextSplitter.builder()
                    .withChunkSize(800)
                    .withMinChunkSizeChars(400)
                    .withMaxNumChunks(Integer.MAX_VALUE)
                    .withMinChunkLengthToEmbed(5)
                    .withKeepSeparator(true)
                    .build();
            List<Document> chunks = splitter.split(documents);

            if (!resourceRepository.existsById(resource.getId())) {
                return;
            }

            Map<String, Object> metadata = buildMetadata(resource, courseId, userId);
            chunks.forEach(chunk -> chunk.getMetadata().putAll(metadata));
            vectorStore.add(chunks);
            resource.markCompleted();
            resourceRepository.save(resource);
        } catch (Exception e) {
            if (resourceRepository.existsById(resource.getId())) {
                resource.markFailed();
                resourceRepository.save(resource);
            }
            throw new RuntimeException(e);
        }
    }

    private Map<String, Object> buildMetadata(TopicResource resource, UUID courseId, UUID userId) {
        return Map.of(
                VectorMetadataKeys.TOPIC_RESOURCE_ID, resource.getId(),
                VectorMetadataKeys.TOPIC_ID, resource.getTopicId(),
                VectorMetadataKeys.USER_ID, userId,
                VectorMetadataKeys.COURSE_ID, courseId,
                VectorMetadataKeys.DOCUMENT_NAME, resource.getFileName()
        );
    }

    private Expression buildFilter(UUID topicId, List<UUID> topicResourceIds) {
        FilterExpressionBuilder builder = new FilterExpressionBuilder();
        FilterExpressionBuilder.Op topicFilter = builder.eq(VectorMetadataKeys.TOPIC_ID, topicId);
        return builder.and(
                topicFilter,
                builder.in(VectorMetadataKeys.TOPIC_RESOURCE_ID, topicResourceIds)
        ).build();
    }
}
