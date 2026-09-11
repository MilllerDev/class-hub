package com.bailon.backend.chat.infrastructure.rest;

import com.bailon.backend.chat.application.usecase.DeleteMessageUseCase;
import com.bailon.backend.chat.application.usecase.ListChatUseCase;
import com.bailon.backend.chat.application.usecase.SendMessageUseCase;
import com.bailon.backend.chat.domain.model.ChatMessage;
import com.bailon.backend.chat.infrastructure.rest.request.SendMessageRequest;
import com.bailon.backend.config.ApiResponse;
import com.bailon.backend.users.infrastructure.security.AppUserPrincipal;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/topic/{topicId}/chat")
@RequiredArgsConstructor
public class ChatController {
    private final SendMessageUseCase sendMessage;
    private final ListChatUseCase getMessages;
    private final DeleteMessageUseCase deleteMessage;

    @PostMapping("/messages")
    @PreAuthorize("hasAuthority('STUDENT')")
    public ResponseEntity<ApiResponse<ChatMessage>> ask(
            @AuthenticationPrincipal AppUserPrincipal auth,
            @PathVariable UUID topicId,
            @Valid @RequestBody SendMessageRequest req
    ) {
        ChatMessage message = sendMessage.send(
                auth.user().getId(), topicId, req.question(), req.resourceIds()
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(
                ApiResponse.success("Pregunta respondida", message)
        );
    }

    @GetMapping("/messages")
    @PreAuthorize("hasAuthority('STUDENT')")
    public ResponseEntity<ApiResponse<List<ChatMessage>>> history(
            @AuthenticationPrincipal AppUserPrincipal auth,
            @PathVariable UUID topicId
    ) {
        List<ChatMessage> messages = getMessages.list(auth.user().getId(), topicId);
        return ResponseEntity.ok(ApiResponse.success("Historial del chat encontrado", messages));
    }

    @DeleteMapping("/messages/{messageId}")
    @PreAuthorize("hasAuthority('STUDENT')")
    public ResponseEntity<ApiResponse<Void>> delete(
            @AuthenticationPrincipal AppUserPrincipal auth,
            @PathVariable UUID topicId,
            @PathVariable UUID messageId
    ) {
        deleteMessage.delete(auth.user().getId(), topicId, messageId);
        return ResponseEntity.ok(ApiResponse.success("Mensaje eliminado exitosamente", null));
    }
}
