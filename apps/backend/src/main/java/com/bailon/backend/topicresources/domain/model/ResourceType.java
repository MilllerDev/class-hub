package com.bailon.backend.topicresources.domain.model;

public enum ResourceType {
    PDF,
    DOCX,
    PPTX;

    public boolean matches(String fileName) {
        return fileName.toLowerCase().endsWith("." + name().toLowerCase());
    }
}
