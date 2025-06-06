# Docker & Containerization Guide

## 1. Overview
Docker is used to containerize the Task Management System for local development, testing, and production deployment.

## 2. Dockerfile
- Builds the Spring Boot application image
- Installs dependencies, copies JAR, exposes port 8080
- Uses multi-stage build for smaller images

## 3. docker-compose.yml
- Orchestrates app and MySQL containers for local dev
- Defines services, networks, volumes
- Usage:
  ```bash
  docker-compose up --build
  docker-compose down
  ```

## 4. Environment Variables
- Set in `docker-compose.yml` or passed at runtime
- Examples:
  - `SPRING_PROFILES_ACTIVE=dev`
  - `MYSQL_ROOT_PASSWORD=secret`
  - `DB_HOST=db`

## 5. Common Commands
- Build image: `docker build -t task-mgmt-app .`
- Run container: `docker run -p 8080:8080 task-mgmt-app`
- View logs: `docker-compose logs app`
- Stop all: `docker-compose down`

## 6. Best Practices
- Use `.dockerignore` to exclude unnecessary files
- Tag images with version/commit
- Use environment variables for secrets/config
- Clean up unused images/volumes regularly

## 7. References
- [Dockerfile](../docker/Dockerfile)
- [docker-compose.yml](../docker/docker-compose.yml) 