package com.example.taskmanagement.repository;

import com.example.taskmanagement.model.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Repository for Task entity.
 */
@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    /**
     * Find tasks by project ID.
     *
     * @param projectId The project ID
     * @return List of tasks in the project
     */
    List<Task> findByProjectId(Long projectId);

    /**
     * Find tasks by assignee ID.
     *
     * @param assigneeId The assignee ID
     * @return List of tasks assigned to the user
     */
    List<Task> findByAssigneeId(Long assigneeId);

    /**
     * Find tasks by status.
     *
     * @param status The task status
     * @param pageable Pagination information
     * @return Page of tasks with the specified status
     */
    Page<Task> findByStatus(Task.TaskStatus status, Pageable pageable);

    /**
     * Find tasks by project ID and status.
     *
     * @param projectId The project ID
     * @param status The task status
     * @return List of tasks in the project with the specified status
     */
    List<Task> findByProjectIdAndStatus(Long projectId, Task.TaskStatus status);

    /**
     * Find tasks due before a specific date.
     *
     * @param dueDate The due date
     * @param pageable Pagination information
     * @return Page of tasks due before the specified date
     */
    Page<Task> findByDueDateBefore(LocalDateTime dueDate, Pageable pageable);

    /**
     * Find overdue tasks assigned to a user.
     *
     * @param assigneeId The assignee ID
     * @param now The current date/time
     * @return List of overdue tasks assigned to the user
     */
    @Query("SELECT t FROM Task t WHERE t.assignee.id = :assigneeId AND t.dueDate < :now AND t.status != 'DONE'")
    List<Task> findOverdueTasksByAssignee(@Param("assigneeId") Long assigneeId, @Param("now") LocalDateTime now);

    /**
     * Count tasks by status in a project.
     *
     * @param projectId The project ID
     * @param status The task status
     * @return Count of tasks with the specified status in the project
     */
    long countByProjectIdAndStatus(Long projectId, Task.TaskStatus status);

    /**
     * Find tasks by title containing the search term.
     *
     * @param searchTerm The search term
     * @param pageable Pagination information
     * @return Page of tasks with titles containing the search term
     */
    Page<Task> findByTitleContainingIgnoreCase(String searchTerm, Pageable pageable);
}

