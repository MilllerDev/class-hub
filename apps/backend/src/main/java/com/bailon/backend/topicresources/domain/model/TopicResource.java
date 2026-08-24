package com.bailon.backend.topicresources.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TopicResource {
    private UUID id;
    private String fileName;
    private String fileUrl;
    private ResourceType fileType;
    private ProcessingStatus processingStatus;
    private UUID topicId;
    private LocalDateTime createdAt;

    public void makeSaving(UUID topicId) {
        this.topicId = topicId;
        this.processingStatus = ProcessingStatus.PENDING;
    }

    public void markProcessing() {
        this.processingStatus = ProcessingStatus.PROCESSING;
    }

    public void markCompleted() {
        this.processingStatus = ProcessingStatus.COMPLETED;
    }

    public void markFailed() {
        this.processingStatus = ProcessingStatus.FAILED;
    }
}
