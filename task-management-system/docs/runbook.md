# Operations Runbook

## 1. Overview
This runbook provides procedures for responding to common incidents and operational issues in the Task Management System.

## 2. Common Issues & Solutions
- **Build/Deploy Failure:**
  - Check Jenkins logs for errors
  - Review recent code changes
  - Retry pipeline or rollback to previous build
- **App Not Starting:**
  - Check Docker container logs
  - Ensure DB is running and accessible
  - Verify environment variables
- **Database Connection Error:**
  - Check DB container/service status
  - Review DB credentials and network config
- **Out of Memory/Crash:**
  - Check JVM and container memory limits
  - Review logs for stack traces
  - Restart service if needed

## 3. Logs & Monitoring
- **Application Logs:**
  - View with `docker-compose logs app` or in Jenkins console
- **Monitoring:**
  - Use Spring Actuator endpoints (`/actuator/health`)
  - Set up alerts for failed builds/deployments

## 4. Restart & Recovery
- **Restart App:**
  - `docker-compose restart app`
  - Or restart via Jenkins pipeline
- **Rollback:**
  - Redeploy previous Docker image/tag
  - Restore DB from backup if needed

## 5. Contact & Escalation
- Notify DevOps or engineering lead for unresolved issues
- Escalate to cloud provider or DB admin for infrastructure problems

## 6. References
- [progress.md](./progress.md)
- [deployment.md](./deployment.md)
- [ci-cd.md](./ci-cd.md) 