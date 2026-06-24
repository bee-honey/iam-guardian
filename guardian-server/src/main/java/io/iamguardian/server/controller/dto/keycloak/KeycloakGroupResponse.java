package io.iamguardian.server.controller.dto.keycloak;

public record KeycloakGroupResponse(
        String id,
        String name,
        String path
) {
}
