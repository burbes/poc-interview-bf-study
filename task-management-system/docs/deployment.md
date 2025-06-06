# Deployment Guide

## 1. Overview
This document describes how to deploy the Task Management System in local, staging, and production environments using Docker, Jenkins, and Ansible.

## 2. Prerequisites
- Java 17
- Maven
- Docker & Docker Compose
- Jenkins (for CI/CD)
- Ansible (for configuration)
- MySQL (or use Docker container)

## 3. Local Deployment (Docker Compose)
1. Clone the repository
2. Build the application:
   ```bash
   mvn clean package
   ```
3. Start services:
   ```bash
   cd task-management-system/docker
   docker-compose up --build
   ```
4. Access the app at [http://localhost:8080](http://localhost:8080)

## 4. Staging/Production Deployment
### Jenkins CI/CD Pipeline
- Code pushed to `main`/`develop` triggers Jenkins pipeline
- Pipeline stages: build, test, package, Docker image build, push to registry, deploy
- Multi-environment deploy via Jenkins parameters

### Docker
- Dockerfile builds the app image
- Images are tagged and pushed to registry (e.g., JFrog Artifactory)
- Used in deployment (compose or orchestrator)

### Ansible
- Playbooks configure environment variables, secrets, and deploy containers
- Inventory files define target hosts
- Usage:
  ```bash
  ansible-playbook -i inventory/<env> playbooks/deploy.yml
  ```

## 5. Rollback Procedures
- Jenkins pipeline supports rollback to previous image/tag
- Manual rollback: redeploy previous Docker image or restore DB backup

## 6. Monitoring & Health Checks
- Spring Actuator endpoints: `/actuator/health`, `/actuator/info`
- Log4j2 for logs
- Alerts for failed builds/deployments (Jenkins, email, Slack)

## 7. Troubleshooting & Runbook
- Check Jenkins build logs for errors
- Check Docker container logs:
  ```bash
  docker-compose logs <service>
  ```
- Common issues: port conflicts, DB connection, out-of-memory
- Restart services as needed

## 8. References
- [Jenkinsfile](../jenkins/Jenkinsfile)
- [Dockerfile](../docker/Dockerfile)
- [docker-compose.yml](../docker/docker-compose.yml)
- [Ansible playbooks](../ansible/playbooks/) 