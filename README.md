# IAM Guardian

IAM Guardian is an open-source identity security graph and risk analysis platform for Keycloak.

Instead of treating identity as a collection of users and roles, IAM Guardian models Keycloak deployments as a graph of users, groups, roles, clients, service accounts, and permissions.

The platform helps answer questions such as:

- Who has direct or indirect administrative access?
- Which service accounts are overprivileged?
- What is the blast radius if an account is compromised?
- Which roles create privilege escalation paths?
- Which applications can access sensitive resources?

## Vision

Modern organizations rely on identity as the first layer of security.

However, most identity platforms provide limited visibility into how permissions propagate through users, groups, composite roles, service accounts, and applications.

IAM Guardian builds a security graph on top of Keycloak and provides:

- Identity relationship mapping
- Privilege escalation detection
- Risk scoring
- Blast radius analysis
- AI-assisted investigation and remediation recommendations

## Core Concepts

### Nodes

- User
- Group
- Role
- Client
- Service Account
- Realm

### Relationships

- MEMBER_OF
- HAS_ROLE
- IMPLIES
- CAN_ACCESS
- OWNS
- IMPERSONATES

## Example

Given:

Alice → Platform Admins → Realm Admin

IAM Guardian can identify:

- Alice has indirect administrative access
- Compromise of Alice results in realm-wide impact
- The privilege path responsible for the risk

## Roadmap

### Phase 1
- Keycloak integration
- Identity graph ingestion
- Basic privilege path analysis

### Phase 2
- Risk scoring engine
- Security findings
- Blast radius reports

### Phase 3
- AI-powered investigation assistant
- Natural language security analysis
- Remediation recommendations

## Status

Early development.

Contributions, ideas, and feedback are welcome.