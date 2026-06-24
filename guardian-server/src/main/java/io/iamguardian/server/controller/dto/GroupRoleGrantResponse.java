package io.iamguardian.server.controller.dto;

import java.util.List;

public record GroupRoleGrantResponse(
        KeycloakGroupResponse group,
        List<KeycloakRoleResponse> rolesGranted
) {
}
