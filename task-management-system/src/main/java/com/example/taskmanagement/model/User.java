package com.example.taskmanagement.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.NaturalId;

import java.util.HashSet;
import java.util.Set;

/**
 * Entity representing a user in the system.
 */
@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(min = 3, max = 50)
    @Column(name = "username", length = 50, unique = true, nullable = false)
    private String username;

    @NotBlank
    @Size(max = 100)
    @Email
    @NaturalId
    @Column(name = "email", length = 100, unique = true, nullable = false)
    private String email;

    @NotBlank
    @Size(max = 100)
    @Column(name = "full_name", length = 100, nullable = false)
    private String fullName;

    @NotBlank
    @Column(name = "password", length = 255, nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", length = 20, nullable = false)
    private UserRole role;

    @Column(name = "enabled", nullable = false)
    private Boolean enabled;

    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private Set<Project> ownedProjects = new HashSet<>();

    @OneToMany(mappedBy = "assignee")
    @Builder.Default
    private Set<Task> assignedTasks = new HashSet<>();

    @ManyToMany(mappedBy = "members")
    @Builder.Default
    private Set<Project> projects = new HashSet<>();

    /**
     * Enum representing user roles in the system.
     */
    public enum UserRole {
        ADMIN, USER, MANAGER
    }

    /**
     * Pre-persist hook to set default values.
     */
    @PrePersist
    public void prePersist() {
        if (this.enabled == null) {
            this.enabled = true;
        }
        if (this.role == null) {
            this.role = UserRole.USER;
        }
    }
}

