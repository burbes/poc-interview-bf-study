package com.example.taskmanagement.repository;

import com.example.taskmanagement.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository for User entity.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Find a user by username.
     *
     * @param username The username
     * @return Optional containing the user if found
     */
    Optional<User> findByUsername(String username);

    /**
     * Find a user by email.
     *
     * @param email The email
     * @return Optional containing the user if found
     */
    Optional<User> findByEmail(String email);

    /**
     * Check if a username exists.
     *
     * @param username The username
     * @return True if the username exists
     */
    boolean existsByUsername(String username);

    /**
     * Check if an email exists.
     *
     * @param email The email
     * @return True if the email exists
     */
    boolean existsByEmail(String email);

    /**
     * Find users by role.
     *
     * @param role The role
     * @return List of users with the specified role
     */
    List<User> findByRole(User.UserRole role);

    /**
     * Find users by project ID (members of a project).
     *
     * @param projectId The project ID
     * @return List of users who are members of the project
     */
    @Query("SELECT u FROM User u JOIN u.projects p WHERE p.id = :projectId")
    List<User> findByProjectId(@Param("projectId") Long projectId);
}

