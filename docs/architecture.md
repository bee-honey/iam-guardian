# Architecture

## High Level

Keycloak serves as the source of truth for identity data.

IAM Guardian periodically ingests identity metadata and builds an internal graph representation.

text Keycloak     |     v Identity Ingestor     |     v Graph Builder     |     v Risk Engine     |     +----> Findings API     |     +----> AI Investigation Agent 

## Components

### Identity Ingestor

Responsible for:

- Users
- Groups
- Roles
- Composite Roles
- Clients
- Service Accounts

### Graph Builder

Builds relationships between identity entities.

Examples:

User -> MEMBER_OF -> Group

Group -> HAS_ROLE -> Role

Role -> IMPLIES -> Role

### Risk Engine

Performs deterministic analysis.

Examples:

- Direct admin access
- Indirect admin access
- Excessive privilege inheritance
- Overprivileged service accounts

### AI Investigation Agent

Provides human-friendly explanations of findings while relying on deterministic analysis as the source of truth.