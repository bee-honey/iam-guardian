package io.iamguardian.server.controller.dto.ai;

public record RemediationProposalResponse(
        String action,
        String userId,
        String username,
        String groupId,
        String groupName,
        String reason
) {
}
