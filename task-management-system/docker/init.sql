-- Initialize database if it doesn't exist
CREATE DATABASE IF NOT EXISTS task_management_dev;
USE task_management_dev;

-- Grant privileges
GRANT ALL PRIVILEGES ON task_management_dev.* TO 'task_user'@'%';
FLUSH PRIVILEGES;

-- Set UTF-8 charset
ALTER DATABASE task_management_dev CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

