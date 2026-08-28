package com.bailon.backend.ragquery.application.service;

import com.bailon.backend.ragquery.application.port.LlmChatPort;
import com.bailon.backend.ragquery.application.port.ResourceVectorSearchPort;
import com.bailon.backend.ragquery.application.usecase.MakeQuery;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class MakeQueryService implements MakeQuery {
    private final ResourceVectorSearchPort vectorSearch;
    private final LlmChatPort llmChat;

    public MakeQueryService(ResourceVectorSearchPort vectorSearch, LlmChatPort llmChat) {
        this.vectorSearch = vectorSearch;
        this.llmChat = llmChat;
    }

    @Override
    public String makeQuery(UUID topicId, List<UUID> resourcesId, String question) {
        List<String> contextChunks = vectorSearch.searchContext(question, topicId, resourcesId);
        if (contextChunks.isEmpty()) {
            return "No tengo esa información.";
        }

        String context = String.join("\n---\n", contextChunks);
        String prompt = """
                Responde la pregunta basándote únicamente en el siguiente contexto.
                Si la respuesta no está en el contexto, dí que no tienes esa información.
                
                Contexto:
                %s
                Pregunta: %s
                """.formatted(context, question);

        return llmChat.complete(prompt);
    }
}
