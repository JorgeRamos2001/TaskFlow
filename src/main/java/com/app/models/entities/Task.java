package com.app.models.entities;

import com.app.models.enums.TaskPriority;
import com.app.models.enums.TaskState;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "tasks")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(nullable = false)
    private String title;
    @Column(columnDefinition = "TEXT", nullable = false)
    private String description;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TaskPriority priority;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TaskState state;
    @CreationTimestamp
    @Column(name ="created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
    @Column(name = "completed_at")
    private LocalDateTime completedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id")
    private Project project;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return id != null && Objects.equals(id, task.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
