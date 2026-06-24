package io.iamguardian.server.controller.dto.ai;

import io.iamguardian.server.controller.dto.keycloak.ElevatedAccessInsightResponse;

import java.util.List;

public record AgentInvestigationResponse(
        String answer,
        List<ElevatedAccessInsightResponse> findings
) {
}
