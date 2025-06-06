package com.example.taskmanagement.repository;

import com.example.taskmanagement.model.Project;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository for Project entity.
 */
@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {

    /**
     * Find projects owned by a user.
     *
     * @param userId The user ID
     * @return List of projects owned by the user
     */
    List<Project> findByOwnerId(Long userId);

    /**
     * Find projects by status.
     *
     * @param status The project status
     * @param pageable Pagination information
     * @return Page of projects with the specified status
     */
    Page<Project> findByStatus(Project.ProjectStatus status, Pageable pageable);

    /**
     * Find projects where a user is a member.
     *
     * @param userId The user ID
     * @return List of projects where the user is a member
     */
    @Query("SELECT p FROM Project p JOIN p.members m WHERE m.id = :userId")
    List<Project> findByMemberId(@Param("userId") Long userId);

    /**
     * Find projects by name containing the search term.
     *
     * @param searchTerm The search term
     * @param pageable Pagination information
     * @return Page of projects with names containing the search term
     */
    Page<Project> findByNameContainingIgnoreCase(String searchTerm, Pageable pageable);

    /**
     * Count projects by status.
     *
     * @param status The project status
     * @return Count of projects with the specified status
     */
    long countByStatus(Project.ProjectStatus status);
}

