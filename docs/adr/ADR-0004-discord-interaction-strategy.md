# ADR-0004 Discord Interaction Strategy

## Status

Accepted

## Context

Dr Marut is delivered through Discord, but the core product must remain a rules engine rather than a chat bot.

## Decision

Treat Discord as an adapter. Slash commands are the primary interaction mode; mentions remain secondary support. Business logic must not live inside JDA listeners.

## Consequences

- Discord startup and connectivity belong to the adapter boundary.
- Future use cases can be reused outside Discord.
- Slash commands remain the default UX path for the MVP.
