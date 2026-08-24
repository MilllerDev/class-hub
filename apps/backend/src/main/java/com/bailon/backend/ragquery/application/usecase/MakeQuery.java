package com.bailon.backend.ragquery.application.usecase;

import java.util.List;
import java.util.UUID;

public interface MakeQuery {
    String makeQuery(UUID topicId, List<UUID> resourcesId, String question);
}
