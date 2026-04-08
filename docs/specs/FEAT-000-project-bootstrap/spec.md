# FEAT-000 Project Bootstrap Spec

## Context

The repository started as a plain Maven scaffold with a placeholder `main()` and no Spring Boot foundation.

## Problem

The project lacked the technical base required by `.codex/AGENTS.md`: Spring Boot, runtime configuration, health checks, persistence bootstrap, CI, and docs structure.

## Objective

Create a production-oriented bootstrap for Dr Marut that is ready for the next features.

## Scope

- Spring Boot application bootstrap
- Java 21 Maven baseline
- JDA integration boundary
- PostgreSQL + Flyway bootstrap
- Actuator health and info
- CI workflow
- Initial docs, ADRs, and specs structure

## Out of Scope

- Rule ingestion
- Search and ranking
- Discord command business behavior
- LLM integration

## Functional Requirements

- The app must boot as a Spring Boot application.
- Discord startup must be conditional on explicit configuration.
- The app must expose health and info endpoints.
- The app must run Flyway migrations on startup.
- The repository must include local PostgreSQL bootstrap via Compose.

## Non-Functional Requirements

- Use Java 21.
- Keep Discord isolated as an adapter.
- Keep package organization aligned with package-by-feature.

## Risks

- Local machines without JDK 21 cannot compile the project.
- Discord tokens must remain outside versioned runtime configuration.

## Acceptance Criteria

- Application starts with Spring Boot.
- Health endpoint is available.
- PostgreSQL migration runs successfully.
- CI runs `mvn test` on JDK 21.
