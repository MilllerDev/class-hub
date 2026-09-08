package com.bailon.backend.topicresources.domain.exception;

import com.bailon.backend.config.exceptions.ApiException;
import org.springframework.http.HttpStatus;

public class TopicResourceNotFoundException extends ApiException {
    public TopicResourceNotFoundException(String message) {
        super(HttpStatus.NOT_FOUND, message);
    }
}