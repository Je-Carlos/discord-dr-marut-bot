# Dr Marut

Dr Marut is a Discord bot focused on Pathfinder 2e rules lookup. This repository now provides the foundation bootstrap for the application: Spring Boot, JDA integration boundary, PostgreSQL + Flyway, Actuator health checks, CI, and the initial docs/ADR/spec structure described in `.codex/AGENTS.md`.

## Requirements

- JDK 21
- Maven 3.9+
- Docker Desktop or another local Docker runtime

## Clone and download

Clone the repository with the PF2E submodule already initialized:

```bash
git clone --recurse-submodules https://github.com/Je-Carlos/discord-dr-marut-bot.git
cd discord-dr-marut-bot
```

If you already cloned the repository without submodules, run:

```bash
git submodule update --init --recursive
```

## PF2E data submodule

The `pf2e-data/` directory is a Git submodule that points to the official PF2E repository:

```bash
git submodule status
```

To pull the latest submodule content configured by this repository:

```bash
git submodule update --init --recursive
```

To fetch newer upstream changes inside the submodule on purpose:

```bash
git submodule update --remote --merge
```

Use `--remote --merge` only when you intentionally want to advance the pinned PF2E revision and then commit the new submodule pointer in this repository.

## Local bootstrap

1. Start PostgreSQL:

```bash
docker compose up -d
```

2. Export environment variables if you want Discord enabled:

```bash
set DRMARUT_DISCORD_ENABLED=true
set DRMARUT_DISCORD_TOKEN=your-token
set DRMARUT_DISCORD_GUILD_ID=your-guild-id
```

Discord is disabled by default in the `local` profile.

3. Run the application:

```bash
mvn spring-boot:run
```

## Default local configuration

- Profile: `local`
- JDBC URL: `jdbc:postgresql://localhost:5432/drmarut`
- User: `drmarut`
- Password: `drmarut`
- Actuator health: `http://localhost:8080/actuator/health`
- Actuator info: `http://localhost:8080/actuator/info`

## Tests

The project uses:

- JUnit 5
- Spring Boot Test
- Testcontainers for PostgreSQL integration tests

Run:

```bash
mvn test
```

If Docker is not available locally, the Testcontainers-backed integration test is skipped while the unit and configuration tests still run.

## Project structure

- `src/main/java/com/drmarut`: application bootstrap and feature packages
- `src/main/resources`: Spring profiles and Flyway migrations
- `pf2e-data`: PF2E upstream data repository tracked as a submodule
- `docs/adr`: architecture decisions
- `docs/specs`: feature specs
- `docs/product`: product-level documents
- `docs/runbooks`: operational runbooks

## GitHub Secrets

For GitHub Actions, the workflow is prepared to read these repository or organization secrets:

- `DRMARUT_DISCORD_ENABLED`
- `DRMARUT_DISCORD_TOKEN`
- `DRMARUT_DISCORD_GUILD_ID`
- `DRMARUT_DB_URL`
- `DRMARUT_DB_USERNAME`
- `DRMARUT_DB_PASSWORD`

Use GitHub Secrets for CI and future deployment automation. For local execution, continue using environment variables on your machine.

## Security note

Secrets must not be committed. The legacy `src/main/resources/config.properties` file is not part of the new runtime path and should not be used as the source of truth for credentials.
