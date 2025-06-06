# Technical Context & Engineering Guidelines

> **Status:**
> - ✅ Phases 1–3 complete
> - 📈 73%+ test coverage
> - 🚧 DevOps/CI/CD is the next focus
> - Engineering guidelines, security, and operational environment are up to date

## Coding Standards
- Java 17, Spring Boot conventions
- Use of Maven for dependency management
- Consistent code formatting and linting

## Testing Strategies
- Unit tests: JUnit 5, Mockito
- Integration tests: Spring Test
- API tests: Postman, REST Assured
- Code coverage reporting

## Code Review Procedures
- Pull requests required for all changes
- Peer review and feedback
- Use of checklists for best practices

## Security Policies
- Secure password storage (BCrypt)
- JWT authentication and authorization
- Dependency vulnerability scanning

## Branch & PR Management
- `main`: Production-ready
- `develop`: Integration
- Feature branches from `develop`
- Release/hotfix branches as needed

## Operational Environment
- Dockerized services
- Jenkins for CI/CD
- Ansible for configuration
- Monitoring with Spring Actuator
- Logging with Log4j2

## JFrog Artifactory Guidelines
- Use JFrog for storing Maven artifacts (JAR/WAR) and Docker images.
- Configure Jenkins with JFrog credentials (use Jenkins credentials store, never hardcode).
- Artifacts must be versioned by commit/tag.
- Retention policy: keep last 10 builds per branch, clean up old artifacts monthly.
- Rotate JFrog credentials every 90 days.
- Use encrypted connections (HTTPS) for all artifact uploads/downloads.
- Document repository structure (e.g., `libs-release-local`, `docker-local`).
- Troubleshooting: check Jenkins logs, JFrog repo permissions, and network connectivity. 