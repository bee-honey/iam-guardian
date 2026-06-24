package io.iamguardian.server.service;

import io.iamguardian.server.controller.dto.ai.AgentInvestigationRequest;
import io.iamguardian.server.controller.dto.ai.AgentInvestigationResponse;
import io.iamguardian.server.controller.dto.keycloak.ElevatedAccessInsightResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccessInvestigationAgent {
    private final KeycloakAdminService keycloakAdminService;

    public AccessInvestigationAgent(KeycloakAdminService keycloakAdminService) {
        this.keycloakAdminService = keycloakAdminService;
    }

    public AgentInvestigationResponse investigate(AgentInvestigationRequest request) {
        String question = request.question().toLowerCase();
        if (question.contains("elevated") || question.contains("admin")) {
            List<ElevatedAccessInsightResponse> findings =
                    keycloakAdminService.listElevatedUserInsights();
            String answer = buildElevatedAccessAnswer(findings);
            return new AgentInvestigationResponse(answer, findings);
        }

        return new AgentInvestigationResponse(
                "I can currently investigate elevated/admin access.",
                List.of()
        );
    }

    private String buildElevatedAccessAnswer(List<ElevatedAccessInsightResponse> findings) {
        if (findings.isEmpty()) {
            return "No users currently have elevated admin access.";
        }

        String users = findings.stream()
                .map(finding -> finding.user().username())
                .distinct()
                .sorted()
                .reduce((a, b) -> a + ", " + b)
                .orElse("");
        String reasons = findings.stream()
                .map(ElevatedAccessInsightResponse::reason)
                .distinct()
                .sorted()
                .reduce((a, b) -> a + ". " + b)
                .orElse("");

        String verb = findings.stream()
                .map(finding -> finding.user().username())
                .distinct()
                .count() == 1 ? "has" : "have";

        return users + " " + verb + " elevated access. " + reasons;

    }
}
