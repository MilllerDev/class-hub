package com.bailon.backend.ragquery.application.port;

public interface LlmChatPort {
    String complete(String prompt);
}
