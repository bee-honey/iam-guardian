package io.iamguardian.server.controller.dto.keycloak;

public record ElevatedAccessInsightResponse(
        KeycloakUserResponse user,
        String role,
        String reason
) {
}
