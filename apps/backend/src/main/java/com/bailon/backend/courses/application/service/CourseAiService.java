package com.bailon.backend.courses.application.service;

import com.bailon.backend.courses.application.port.CourseAiPort;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

@Service
public class CourseAiService implements CourseAiPort {
    private final ChatClient chatClient;
    private static final String PROMPT_TEMPLATE = """
            Curso: {name}
            Genera una descripción breve (2-3 oraciones, máx. 60 palabras) para este curso.
            Si el nombre no tiene sentido, responde exactamente: "No hay una descripción para ello."
            Responde solo con la descripción, sin comillas ni texto adicional.
            """;

    public CourseAiService(ChatClient chatClient) {
        this.chatClient = chatClient;
    }

    @Override
    public String genereteDetails(String name) {
        return chatClient.prompt().user(u ->
                u.text(PROMPT_TEMPLATE).param("name", name)
        ).call().content();
    }
}
