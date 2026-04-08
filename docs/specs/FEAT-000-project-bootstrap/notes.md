# FEAT-000 Project Bootstrap Notes

- The runtime path no longer depends on `src/main/resources/config.properties`.
- The local environment used during implementation still exposed JDK 17, so repository-level Java 21 enforcement became part of the bootstrap itself.
- This phase intentionally stops before implementing rule lookup behavior.
