package com.bailon.backend.courses.infrastructure.persistence;

import com.bailon.backend.users.infrastructure.persistence.UserEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "courses")
public class CourseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "course_id", nullable = false, updatable = false)
    private UUID id;

    @Column(name = "course_name", nullable = false, length = 80)
    private String name;

    @Column(name = "course_description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "course_slug", nullable = false, unique = true, length = 80)
    private String slug;

    @Column(name = "course_enabled", nullable = false)
    private boolean enabled;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    @OnDelete(action = OnDeleteAction.SET_NULL)
    private UserEntity user;

    @PrePersist
    public void prePersist() {
        createdAt = LocalDateTime.now();
    }
}
