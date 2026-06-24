package io.iamguardian.server.controller.dto;

public record KeycloakUserResponse(
        String id,
        String username,
        String email,
        Boolean enabled
) {
}
