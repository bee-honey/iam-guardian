package io.iamguardian.server.controller.dto.keycloak;

public record KeycloakRoleResponse(
        String id,
        String name,
        String description
) {
}