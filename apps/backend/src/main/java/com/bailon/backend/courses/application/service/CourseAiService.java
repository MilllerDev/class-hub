package com.bailon.backend.courses.application.service;

import com.bailon.backend.courses.application.port.CourseAiPort;
import com.bailon.backend.courses.application.port.CourseRepository;
import com.bailon.backend.courses.domain.model.Course;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class CourseAiService implements CourseAiPort {
    private final ChatClient chatClient;
    private final CourseRepository repository;
    private static final String PROMPT_TEMPLATE = """
            Curso: {name}
            Description: {description}
            Si no hay una descripción entonces genera, en caso hubiese mejoralo manteniendo la información original.
            Para ambos casos la descripción debe ser breve (2-3 oraciones, máx. 60 palabras) para este curso.
            Si el nombre no tiene sentido responde exactamente: "No hay una descripción para ello."
            Si la descripción no tiene sentido, pero si el nombre entonces genera una nueva.
            Responde solo con la descripción, sin comillas ni texto adicional.
            """;

    public CourseAiService(ChatClient chatClient, CourseRepository repository) {
        this.chatClient = chatClient;
        this.repository = repository;
    }

    @Async("aiService")
    @Override
    public void genereteDetails(Course course, String name) {
        String description = chatClient.prompt().user(u -> u
                .text(PROMPT_TEMPLATE)
                .param("name", name)
                .param("description", course.getDescription())
        ).call().content();
        course.setDescription(description);
        repository.save(course);
    }
}
