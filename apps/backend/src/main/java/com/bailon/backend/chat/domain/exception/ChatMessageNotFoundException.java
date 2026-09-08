package com.bailon.backend.chat.domain.exception;

import com.bailon.backend.config.exceptions.ApiException;
import org.springframework.http.HttpStatus;

public class ChatMessageNotFoundException extends ApiException {
    public ChatMessageNotFoundException(String message) {
        super(HttpStatus.NOT_FOUND, message);
    }
}