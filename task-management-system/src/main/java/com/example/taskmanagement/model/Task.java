package com.example.taskmanagement.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;

/**
 * Entity representing a task in the system.
 */
@Entity
@Table(name = "tasks")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Task extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(min = 3, max = 255)
    @Column(name = "title", length = 255, nullable = false)
    private String title;

    @Column(name = "description")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", length = 20, nullable = false)
    private TaskStatus status;

    @Enumerated(EnumType.STRING)
    @Column(name = "priority", length = 20, nullable = false)
    private TaskPriority priority;

    @Column(name = "due_date")
    private LocalDateTime dueDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "assignee_id")
    private User assignee;

    /**
     * Enum representing task status.
     */
    public enum TaskStatus {
        TODO, IN_PROGRESS, REVIEW, DONE
    }

    /**
     * Enum representing task priority.
     */
    public enum TaskPriority {
        LOW, MEDIUM, HIGH, CRITICAL
    }

    /**
     * Pre-persist hook to set default values.
     */
    @PrePersist
    public void prePersist() {
        if (this.status == null) {
            this.status = TaskStatus.TODO;
        }
        if (this.priority == null) {
            this.priority = TaskPriority.MEDIUM;
        }
    }

    /**
     * Assign the task to a user.
     *
     * @param user The user to assign the task to
     * @return The updated task
     */
    public Task assignTo(User user) {
        this.assignee = user;
        if (user != null) {
            user.getAssignedTasks().add(this);
        }
        return this;
    }
}

