# ADR-0003 Foundry Import Versioning

## Status

Accepted

## Context

The PF2E dataset changes over time, and the bot must preserve traceability for imported content.

## Decision

All future imports must preserve source metadata such as commit SHA, release or tag when available, sync timestamps, and execution status.

## Consequences

- Import processes must be auditable.
- The product can explain which version of the source data backed an answer.
- Reindexing must remain deterministic and traceable.
