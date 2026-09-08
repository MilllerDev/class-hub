package com.bailon.backend.topicresources.infrastructure.ai;

import com.bailon.backend.courses.domain.event.CourseDeletedEvent;
import com.bailon.backend.topicresources.domain.event.TopicResourcesDeletedEvent;
import com.bailon.backend.topics.domain.event.TopicDeletedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.ai.vectorstore.filter.FilterExpressionBuilder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
public class VectorCleanupListener {
    private final VectorStore vectorStore;

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void onTopicResourcesDeleted(TopicResourcesDeletedEvent event) {
        FilterExpressionBuilder builder = new FilterExpressionBuilder();
        vectorStore.delete(builder.eq(VectorMetadataKeys.TOPIC_RESOURCE_ID, event.resourceId()).build());
    }

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void onTopicDeleted(TopicDeletedEvent event) {
        FilterExpressionBuilder builder = new FilterExpressionBuilder();
        vectorStore.delete(builder.eq(VectorMetadataKeys.TOPIC_ID, event.topicId()).build());
    }

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void onCourseDeleted(CourseDeletedEvent event) {
        FilterExpressionBuilder builder = new FilterExpressionBuilder();
        vectorStore.delete(builder.eq(VectorMetadataKeys.COURSE_ID, event.courseId()).build());
    }
}
