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
    private ResourceStatus status;
    private UUID topicId;
    private LocalDateTime createdAt;

    public void markSaving() {
        this.status = ResourceStatus.PENDING;
    }

    public void markProcessing() {
        this.status = ResourceStatus.PROCESSING;
    }

    public void markCompleted() {
        this.status = ResourceStatus.COMPLETED;
    }

    public void markFailed() {
        this.status = ResourceStatus.FAILED;
    }
}
