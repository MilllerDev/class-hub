package com.bailon.backend;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.document.Document;
import org.springframework.ai.reader.tika.TikaDocumentReader;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RagService {
    private final VectorStore vectorStore;
    private final ChatClient chatClient;

    public RagService(VectorStore vectorStore, ChatClient chatClient) {
        this.vectorStore = vectorStore;
        this.chatClient = chatClient;
    }

    public void processDocument(MultipartFile file) {
        TikaDocumentReader reader = new TikaDocumentReader(file.getResource());
        List<Document> documents = reader.get();

        TokenTextSplitter splitter = TokenTextSplitter.builder()
                .withChunkSize(100)
                .withMinChunkSizeChars(200)
                .withKeepSeparator(false)
                .build();
        List<Document> chunks = splitter.split(documents);
        vectorStore.add(chunks);
    }

    public String askQuestion(String question) {
        List<Document> similarDocuments = vectorStore.similaritySearch(
                SearchRequest.builder().query(question).topK(2).build()
        );

        String context = similarDocuments.stream()
                .map(Document::getFormattedContent)
                .collect(Collectors.joining("\n---\n"));

        String prompt = """
                Respondé la pregunta basándote únicamente en el siguiente contexto.
                Si la respuesta no está en el contexto, dí que no tenés esa información.
                
                Contexto:
                %s
                
                Pregunta: %s
                """.formatted(context, question);

        return chatClient.prompt()
                .user(prompt)
                .call()
                .content();
    }
}
