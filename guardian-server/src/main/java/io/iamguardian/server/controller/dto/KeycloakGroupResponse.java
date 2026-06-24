package io.iamguardian.server.controller.dto;

public record KeycloakGroupResponse(
        String id,
        String name,
        String path
) {
}
