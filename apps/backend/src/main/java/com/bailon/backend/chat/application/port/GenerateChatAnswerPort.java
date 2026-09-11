package com.bailon.backend.chat.application.port;

import java.util.List;
import java.util.UUID;

public interface GenerateChatAnswerPort {
    String generate(UUID topicId, List<UUID> resourceIds, String question);
}