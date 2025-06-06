package com.example.taskmanagement.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * Entity representing a project in the system.
 */
@Entity
@Table(name = "projects")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Project extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(min = 3, max = 100)
    @Column(name = "name", length = 100, nullable = false)
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", length = 20, nullable = false)
    private ProjectStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id", nullable = false)
    private User owner;

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private Set<Task> tasks = new HashSet<>();

    @ManyToMany
    @JoinTable(
        name = "project_members",
        joinColumns = @JoinColumn(name = "project_id"),
        inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    @Builder.Default
    private Set<User> members = new HashSet<>();

    /**
     * Enum representing project status.
     */
    public enum ProjectStatus {
        ACTIVE, COMPLETED, ON_HOLD, CANCELLED
    }

    /**
     * Pre-persist hook to set default values.
     */
    @PrePersist
    public void prePersist() {
        if (this.status == null) {
            this.status = ProjectStatus.ACTIVE;
        }
    }

    /**
     * Add a member to the project.
     *
     * @param user The user to add as a member
     * @return The updated project
     */
    public Project addMember(User user) {
        members.add(user);
        user.getProjects().add(this);
        return this;
    }

    /**
     * Remove a member from the project.
     *
     * @param user The user to remove
     * @return The updated project
     */
    public Project removeMember(User user) {
        members.remove(user);
        user.getProjects().remove(this);
        return this;
    }

    /**
     * Add a task to the project.
     *
     * @param task The task to add
     * @return The updated project
     */
    public Project addTask(Task task) {
        tasks.add(task);
        task.setProject(this);
        return this;
    }

    /**
     * Remove a task from the project.
     *
     * @param task The task to remove
     * @return The updated project
     */
    public Project removeTask(Task task) {
        tasks.remove(task);
        task.setProject(null);
        return this;
    }
}

