package com.bailon.backend.topicresources.domain.exception;

import com.bailon.backend.config.exceptions.ApiException;
import org.springframework.http.HttpStatus;

public class InvalidResourceFileTypeException extends ApiException {
    public InvalidResourceFileTypeException(String message) {
        super(HttpStatus.BAD_REQUEST, message);
    }
}