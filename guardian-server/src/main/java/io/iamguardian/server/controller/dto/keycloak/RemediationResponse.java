package io.iamguardian.server.controller.dto.keycloak;

public record RemediationResponse(
        String action,
        boolean dryRun,
        String message
) {
}
