# Active Architectural Decisions

> **Status:**
> - ✅ Phases 1–3 complete
> - 📈 73%+ test coverage
> - 🚧 DevOps/CI/CD is the next focus
> - ADRs are up to date with current architecture and decisions

## ADR 1: Use of Spring Boot 3.x
- **Rationale:** Modern, widely adopted Java framework with strong community support.
- **Benefits:** Rapid development, built-in features, easy integration.
- **Risks:** Learning curve for advanced features.
- **Alternatives:** Jakarta EE, Micronaut (rejected for less community support).

## ADR 2: RESTful API with JSON
- **Rationale:** Standard for web APIs, easy integration with frontend and tools.
- **Benefits:** Interoperability, simplicity.
- **Risks:** None significant.
- **Alternatives:** GraphQL (rejected for simplicity).

## ADR 3: MySQL 8.x with Flyway
- **Rationale:** Reliable, widely used RDBMS with strong migration tooling.
- **Benefits:** Data integrity, easy migrations.
- **Risks:** Scaling for very large datasets.
- **Alternatives:** PostgreSQL (viable), MongoDB (not relational).

## ADR 4: JWT Authentication
- **Rationale:** Secure, stateless authentication for APIs.
- **Benefits:** Scalability, security.
- **Risks:** Token leakage, expiration handling.
- **Alternatives:** Session-based auth (less scalable). 