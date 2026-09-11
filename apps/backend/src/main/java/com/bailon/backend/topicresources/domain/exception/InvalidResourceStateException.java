package com.bailon.backend.topicresources.domain.exception;

import com.bailon.backend.config.exceptions.ApiException;
import org.springframework.http.HttpStatus;

public class InvalidResourceStateException extends ApiException {
    public InvalidResourceStateException(String message) {
        super(HttpStatus.CONFLICT, message);
    }
}