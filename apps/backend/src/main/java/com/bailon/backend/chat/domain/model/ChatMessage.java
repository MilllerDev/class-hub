package com.bailon.backend.chat.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ChatMessage {
    private UUID id;
    private UUID userId;
    private UUID topicId;
    private String question;
    private String answer;
    private LocalDateTime createdAt;

    public static ChatMessage ask(UUID userId, UUID topicId, String question, String answer) {
        return new ChatMessage(null, userId, topicId, question, answer, null);
    }
}
