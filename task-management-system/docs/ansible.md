# Ansible Deployment Guide

## 1. Overview
Ansible is used to automate environment configuration and deployment for the Task Management System in staging and production.

## 2. Directory Structure
- `ansible/inventory/`: Host and group definitions
- `ansible/playbooks/`: Playbooks for deployment, config, DB, etc.
- `ansible/templates/`: Jinja2 templates for config files

## 3. Inventory Files
- Define target hosts and groups (e.g., staging, prod)
- Example: `inventory/staging`, `inventory/prod`

## 4. Playbooks Usage
- Deploy app, configure environment, manage DB
- Run with:
  ```bash
  ansible-playbook -i inventory/<env> playbooks/deploy.yml
  ```

## 5. Secrets & Environment Variables
- Use Ansible Vault for sensitive data
- Pass variables via `--extra-vars` or in inventory/group_vars

## 6. Common Commands
- Run playbook: `ansible-playbook -i inventory/staging playbooks/deploy.yml`
- Encrypt secrets: `ansible-vault encrypt group_vars/prod/secrets.yml`
- Decrypt: `ansible-vault decrypt ...`

## 7. Best Practices
- Use vault for all secrets
- Parameterize playbooks for reusability
- Test playbooks in staging before prod
- Keep inventory and playbooks under version control

## 8. References
- [Ansible playbooks](../ansible/playbooks/)
- [Inventory files](../ansible/inventory/)
- [Ansible docs](https://docs.ansible.com/) 