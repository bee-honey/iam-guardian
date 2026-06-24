package io.iamguardian.server.controller.dto;

public record ElevatedAccessInsightResponse(
        KeycloakUserResponse user,
        String role,
        String reason
) {
}
