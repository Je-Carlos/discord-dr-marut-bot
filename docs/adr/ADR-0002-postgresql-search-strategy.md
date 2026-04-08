# ADR-0002 PostgreSQL Search Strategy

## Status

Accepted

## Context

The project needs a reliable local source of truth and a first search path without introducing external search infrastructure too early.

## Decision

Use PostgreSQL as the primary persistence layer and first search substrate, combining normalized fields, exact matching, and room for later text-search enhancements.

## Consequences

- Data remains local and queryable without live GitHub access.
- Flyway migrations become part of the core bootstrap.
- External search engines remain future options, not foundation dependencies.
