# FEAT-000 Project Bootstrap Acceptance

## Given / When / Then

### Context startup

- Given a valid Spring Boot configuration and PostgreSQL datasource
- When the application starts
- Then the Spring context must load and Flyway must apply the bootstrap migration

### Discord disabled by default

- Given the `local` profile without Discord credentials
- When the application starts
- Then the app must boot without trying to connect to Discord

### Discord health reporting

- Given Discord is disabled
- When health is evaluated
- Then the Discord component must report `UNKNOWN`

### CI

- Given a push or pull request
- When GitHub Actions runs
- Then the Maven test suite must execute on JDK 21
