package com.bailon.backend;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/rag")
public class RagController {
    private final RagService ragService;

    public RagController(RagService ragService) {
        this.ragService = ragService;
    }

    @PostMapping("/upload")
    public String upload(@RequestParam MultipartFile file) {
        ragService.processDocument(file);
        return "El documento: " + file.getOriginalFilename() + "fue procesado.";
    }

    @GetMapping("/ask")
    public String ask(@RequestParam String query) {
        return ragService.askQuestion(query);
    }
}
