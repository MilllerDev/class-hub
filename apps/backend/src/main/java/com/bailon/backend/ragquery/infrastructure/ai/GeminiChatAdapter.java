package com.bailon.backend.ragquery.infrastructure.ai;

import com.bailon.backend.ragquery.application.port.LlmChatPort;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Component;

@Component
public class GeminiChatAdapter implements LlmChatPort {
    private final ChatClient chatClient;

    public GeminiChatAdapter(ChatClient chatClient) {
        this.chatClient = chatClient;
    }

    @Override
    public String complete(String prompt) {
        return chatClient.prompt()
                .user(prompt)
                .call()
                .content();
    }
}
