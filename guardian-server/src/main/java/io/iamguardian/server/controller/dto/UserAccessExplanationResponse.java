package io.iamguardian.server.controller.dto;

import java.util.List;

public record UserAccessExplanationResponse(
        KeycloakUserResponse user,
        List<GroupRoleGrantResponse> groupAssignments,
        List<KeycloakRoleResponse> effectiveRoles
) {
}
