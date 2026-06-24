package io.iamguardian.server.controller.dto;

public record KeycloakRoleResponse(
        String id,
        String name,
        String description
) {
}