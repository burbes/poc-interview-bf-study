-- Initial Database Schema for Task Management System

-- Enable FOREIGN_KEY_CHECKS for data integrity
SET FOREIGN_KEY_CHECKS = 1;

-- Users Table
CREATE TABLE users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    email VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    full_name VARCHAR(100) NOT NULL,
    role VARCHAR(20) NOT NULL DEFAULT 'USER',
    enabled BOOLEAN NOT NULL DEFAULT TRUE,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    created_by VARCHAR(50) NOT NULL DEFAULT 'system',
    updated_by VARCHAR(50) NOT NULL DEFAULT 'system',
    INDEX idx_users_username (username),
    INDEX idx_users_email (email)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Projects Table
CREATE TABLE projects (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    description TEXT,
    start_date DATE,
    end_date DATE,
    status VARCHAR(20) NOT NULL DEFAULT 'ACTIVE',
    owner_id BIGINT NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    created_by VARCHAR(50) NOT NULL DEFAULT 'system',
    updated_by VARCHAR(50) NOT NULL DEFAULT 'system',
    CONSTRAINT fk_projects_owner FOREIGN KEY (owner_id) REFERENCES users (id) ON DELETE RESTRICT ON UPDATE CASCADE,
    INDEX idx_projects_status (status),
    INDEX idx_projects_owner (owner_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Tasks Table
CREATE TABLE tasks (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    description TEXT,
    status VARCHAR(20) NOT NULL DEFAULT 'TODO',
    priority VARCHAR(20) NOT NULL DEFAULT 'MEDIUM',
    due_date DATETIME,
    project_id BIGINT NOT NULL,
    assignee_id BIGINT,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    created_by VARCHAR(50) NOT NULL DEFAULT 'system',
    updated_by VARCHAR(50) NOT NULL DEFAULT 'system',
    CONSTRAINT fk_tasks_project FOREIGN KEY (project_id) REFERENCES projects (id) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT fk_tasks_assignee FOREIGN KEY (assignee_id) REFERENCES users (id) ON DELETE SET NULL ON UPDATE CASCADE,
    INDEX idx_tasks_status (status),
    INDEX idx_tasks_priority (priority),
    INDEX idx_tasks_due_date (due_date),
    INDEX idx_tasks_project (project_id),
    INDEX idx_tasks_assignee (assignee_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Project Members Table (Many-to-Many relationship between Projects and Users)
CREATE TABLE project_members (
    project_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    role VARCHAR(20) NOT NULL DEFAULT 'MEMBER',
    joined_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (project_id, user_id),
    CONSTRAINT fk_project_members_project FOREIGN KEY (project_id) REFERENCES projects (id) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT fk_project_members_user FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE ON UPDATE CASCADE,
    INDEX idx_project_members_role (role)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Task Comments Table
CREATE TABLE task_comments (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    task_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    comment TEXT NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT fk_task_comments_task FOREIGN KEY (task_id) REFERENCES tasks (id) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT fk_task_comments_user FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE ON UPDATE CASCADE,
    INDEX idx_task_comments_task (task_id),
    INDEX idx_task_comments_user (user_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Task Attachments Table
CREATE TABLE task_attachments (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    task_id BIGINT NOT NULL,
    file_name VARCHAR(255) NOT NULL,
    file_path VARCHAR(512) NOT NULL,
    file_size BIGINT NOT NULL,
    file_type VARCHAR(100) NOT NULL,
    uploaded_by BIGINT NOT NULL,
    uploaded_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_task_attachments_task FOREIGN KEY (task_id) REFERENCES tasks (id) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT fk_task_attachments_user FOREIGN KEY (uploaded_by) REFERENCES users (id) ON DELETE CASCADE ON UPDATE CASCADE,
    INDEX idx_task_attachments_task (task_id),
    INDEX idx_task_attachments_uploaded_by (uploaded_by)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Task History Table (for auditing changes)
CREATE TABLE task_history (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    task_id BIGINT NOT NULL,
    changed_by BIGINT NOT NULL,
    field_name VARCHAR(50) NOT NULL,
    old_value TEXT,
    new_value TEXT,
    changed_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_task_history_task FOREIGN KEY (task_id) REFERENCES tasks (id) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT fk_task_history_user FOREIGN KEY (changed_by) REFERENCES users (id) ON DELETE CASCADE ON UPDATE CASCADE,
    INDEX idx_task_history_task (task_id),
    INDEX idx_task_history_changed_by (changed_by),
    INDEX idx_task_history_changed_at (changed_at)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Insert initial admin user (password is 'admin' - should be changed immediately in production)
INSERT INTO users (username, email, password, full_name, role, created_by, updated_by)
VALUES ('admin', 'admin@example.com', '$2a$10$lfAYkYUvbKZQhQJqxwIp8uLyzWmUcNp/DjbO8o02CLaGGcBRw0YA.', 'System Administrator', 'ADMIN', 'system', 'system');

