package io.iamguardian.server.controller.dto.keycloak;

public record KeycloakUserResponse(
        String id,
        String username,
        String email,
        Boolean enabled
) {
}
