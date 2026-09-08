package com.bailon.backend.chat.infrastructure.ai;

import com.bailon.backend.chat.application.port.GenerateChatAnswerPort;
import com.bailon.backend.topicresources.application.port.PgVectorResourcePort;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class GenerateResponseService implements GenerateChatAnswerPort {
    private static final String NO_CONTEXT_ANSWER = "No tengo esa información.";
    private final PgVectorResourcePort pgVectorResourcePort;
    private final ChatClient chatClient;

    public GenerateResponseService(PgVectorResourcePort pgVectorResourcePort, ChatClient chatClient) {
        this.pgVectorResourcePort = pgVectorResourcePort;
        this.chatClient = chatClient;
    }

    @Override
    public String generate(UUID topicId, List<UUID> resourceIds, String question) {
        List<String> contextChunks = pgVectorResourcePort.searchContext(question, topicId, resourceIds);
        if (contextChunks.isEmpty()) {
            return NO_CONTEXT_ANSWER;
        }

        String context = String.join("\n---\n", contextChunks);
        String prompt = """
                Responde la pregunta basándote únicamente en el siguiente contexto.
                Si la respuesta no está en el contexto, dí que no tienes esa información.
                
                Contexto:
                %s
                Pregunta: %s
                """.formatted(context, question);

        return chatClient.prompt().user(prompt).call().content();
    }
}