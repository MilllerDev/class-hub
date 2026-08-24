package com.bailon.backend.ragquery.application.port;

import java.util.List;
import java.util.UUID;

public interface ResourceVectorSearchPort {
    List<String> searchContext(String query, UUID topicId, List<UUID> topicResourceIds);
}
