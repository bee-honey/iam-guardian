package io.iamguardian.server.controller.dto.keycloak;

import java.util.List;

public record UserAccessResponse(
        KeycloakUserResponse user,
        List<KeycloakGroupResponse> groups,
        List<KeycloakRoleResponse> effectiveRoles
) {
}
