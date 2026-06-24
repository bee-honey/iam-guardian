package io.iamguardian.server.controller.dto.ai;

import java.util.List;

public record AgentRemediationResponse(
        String summary,
        List<RemediationProposalResponse> proposals
) {
}
