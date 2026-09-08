package com.bailon.backend.topicresources.domain.exception;

import com.bailon.backend.config.exceptions.ApiException;
import org.springframework.http.HttpStatus;

public class TopicNotOwnedByTeacherException extends ApiException {
    public TopicNotOwnedByTeacherException(String message) {
        super(HttpStatus.FORBIDDEN, message);
    }
}