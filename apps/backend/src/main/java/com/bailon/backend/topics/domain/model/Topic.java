package com.bailon.backend.topics.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Topic {
    private UUID id;
    private String title;
    private UUID courseId;
    private LocalDateTime createdAt;
}
