package com.bailon.backend.topics.domain.exception;

import com.bailon.backend.config.exceptions.ApiException;
import org.springframework.http.HttpStatus;

public class TopicNotFoundException extends ApiException {
    public TopicNotFoundException(String message) {
        super(HttpStatus.NOT_FOUND, message);
    }
}
