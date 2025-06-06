# System Design & Architecture

> **Current Phase:** 🚧 DevOps & Deployment (Phase 4)
> - CI/CD, Docker, and Ansible are the next focus areas.
> - High-level and low-level design, C4, and UML are up to date with current implementation.

## High-Level Design
The system is a layered Spring Boot application with RESTful APIs, a MySQL database, and CI/CD integration.

### Main Components
- API Layer (Controllers)
- Service Layer (Business Logic)
- Data Layer (Repositories, JPA)
- Security Layer (JWT, Spring Security)
- Configuration Layer (YAML, Ansible)

### Integration Points
- Jenkins (CI/CD)
- JFrog Artifactory (Artifacts)
- Docker (Containerization)
- Ansible (Configuration)
- Postman (API Testing)

## Low-Level Design
- DTOs for data transfer
- Exception handling with custom exceptions
- Service classes for business logic
- Repository interfaces for data access

## C4 Model
- **Context:** Application interacts with users, CI/CD, database, artifact repo
- **Container:** Spring Boot app, MySQL DB, Jenkins, JFrog, Docker
- **Component:** Controllers, Services, Repositories, Security, Config
- **Code:** Follows Java/Spring best practices

## UML Diagrams
- Class, sequence, and activity diagrams to be added as the implementation progresses.

## CI/CD Artifact Flow (Jenkins ↔ JFrog ↔ Deployment)

```mermaid
sequenceDiagram
    participant Dev as Developer
    participant Jenkins
    participant JFrog as JFrog Artifactory
    participant Deploy as Deployment Target
    Dev->>Jenkins: Push code/PR
    Jenkins->>Jenkins: Build, Test, Package
    Jenkins->>JFrog: Upload artifact (JAR/Docker)
    JFrog-->>Jenkins: Confirm upload
    Jenkins->>Deploy: Deploy using artifact from JFrog
    Deploy-->>Jenkins: Deployment status
```

### Notes
- Jenkins uses credentials to push/pull from JFrog.
- All deployments pull artifacts from JFrog, not from local builds.
- Rollbacks use previous artifact versions from JFrog. 