# ADR-0001 Package by Feature

## Status

Accepted

## Context

The project started from a flat scaffold and needed an organizing principle before features multiplied.

## Decision

Organize the codebase primarily by feature, not by global layers.

## Consequences

- Discord integration remains isolated as an adapter.
- Future ingestion, search, explanation, and admin capabilities can evolve independently.
- Avoid global `service`, `controller`, and `repository` packages.
