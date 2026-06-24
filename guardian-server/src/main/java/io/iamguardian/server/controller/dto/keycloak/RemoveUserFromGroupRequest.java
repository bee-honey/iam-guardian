package io.iamguardian.server.controller.dto.keycloak;

public record RemoveUserFromGroupRequest(
        String userId,
        String groupId,
        boolean dryRun
) {
}
