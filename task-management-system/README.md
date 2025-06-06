# Task Management System

A comprehensive task management system built with Spring Boot and modern tools.

## Project Overview

This task management system is a RESTful application designed to demonstrate proficiency in Java development and associated technologies. It showcases backend development, API design, testing, CI/CD pipelines, and DevOps practices.

## Technology Stack

- **Backend**: Java 17, Spring Boot 3.x
- **Database**: MySQL 8.x, Flyway for migrations
- **Security**: Spring Security with JWT
- **API Documentation**: Swagger/OpenAPI 3.0
- **Build Tool**: Maven
- **Logging**: Log4j2
- **Testing**: JUnit 5, Mockito
- **DevOps**: Docker, Jenkins, Ansible
- **Artifact Repository**: JFrog Artifactory

## Getting Started

### Prerequisites

- Java 17 or higher
- Maven 3.6 or higher
- Docker and Docker Compose
- MySQL 8.0 (or use the provided Docker setup)

### Running Locally

1. Clone the repository:
   ```bash
   git clone https://github.com/yourusername/task-management-system.git
   cd task-management-system
   ```

2. Run with Docker Compose:
   ```bash
   cd docker
   docker-compose up -d
   ```

   This will start:
   - The application on port 8080
   - MySQL database on port 3306
   - Jenkins on port 8081
   - Adminer (DB management tool) on port 8082

3. Alternatively, run without Docker:
   ```bash
   mvn spring-boot:run
   ```

### Accessing the Application

- **API**: http://localhost:8080/api
- **Swagger UI**: http://localhost:8080/api/swagger-ui.html
- **API Docs**: http://localhost:8080/api/api-docs
- **Actuator**: http://localhost:8080/api/actuator
- **Jenkins**: http://localhost:8081
- **Adminer**: http://localhost:8082

### Running Tests

```bash
mvn test
```

## Project Structure

```
task-management-system/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/example/taskmanagement/
│   │   │       ├── config/        # Configuration classes
│   │   │       ├── controller/    # REST controllers
│   │   │       ├── dto/           # Data Transfer Objects
│   │   │       ├── exception/     # Custom exceptions
│   │   │       ├── model/         # Domain models
│   │   │       ├── repository/    # Data access layer
│   │   │       ├── security/      # Security configurations
│   │   │       ├── service/       # Business logic
│   │   │       └── util/          # Utility classes
│   │   └── resources/             # Configuration files
│   └── test/                      # Test classes
├── docker/                        # Docker configurations
├── jenkins/                       # Jenkins pipeline
├── ansible/                       # Ansible playbooks
└── docs/                          # Documentation
```

## API Endpoints

| Method | URL                           | Description                    |
|--------|-------------------------------|--------------------------------|
| POST   | /api/auth/login               | User login                     |
| POST   | /api/auth/register            | User registration              |
| GET    | /api/users                    | Get all users                  |
| GET    | /api/users/{id}               | Get user by ID                 |
| PUT    | /api/users/{id}               | Update user                    |
| DELETE | /api/users/{id}               | Delete user                    |
| GET    | /api/projects                 | Get all projects               |
| POST   | /api/projects                 | Create new project             |
| GET    | /api/projects/{id}            | Get project by ID              |
| PUT    | /api/projects/{id}            | Update project                 |
| DELETE | /api/projects/{id}            | Delete project                 |
| GET    | /api/projects/{id}/tasks      | Get tasks for project          |
| POST   | /api/tasks                    | Create new task                |
| GET    | /api/tasks/{id}               | Get task by ID                 |
| PUT    | /api/tasks/{id}               | Update task                    |
| DELETE | /api/tasks/{id}               | Delete task                    |

## CI/CD Pipeline

The project includes a comprehensive CI/CD pipeline using Jenkins that:

1. Builds the application
2. Runs tests
3. Performs code quality analysis
4. Builds and publishes Docker images
5. Deploys to development/staging/production environments

## Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add some amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

## License

This project is licensed under the MIT License - see the LICENSE file for details.

