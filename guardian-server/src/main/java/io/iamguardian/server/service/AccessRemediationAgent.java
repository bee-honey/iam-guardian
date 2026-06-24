package io.iamguardian.server.service;

import io.iamguardian.server.controller.dto.ai.AgentRemediationResponse;
import io.iamguardian.server.controller.dto.ai.RemediationProposalResponse;
import io.iamguardian.server.controller.dto.keycloak.ElevatedAccessInsightResponse;
import io.iamguardian.server.controller.dto.keycloak.GroupRoleGrantResponse;
import io.iamguardian.server.controller.dto.keycloak.UserAccessExplanationResponse;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AccessRemediationAgent {

    private final KeycloakAdminService keycloakAdminService;

    public AccessRemediationAgent(KeycloakAdminService keycloakAdminService) {
        this.keycloakAdminService = keycloakAdminService;
    }

    public AgentRemediationResponse proposeRemediation(String question) {
        List<ElevatedAccessInsightResponse> elevatedUsers =
                keycloakAdminService.listElevatedUserInsights();

        List<RemediationProposalResponse> proposals = new ArrayList<>();
        for (ElevatedAccessInsightResponse insight : elevatedUsers) {
            UserAccessExplanationResponse explanation =
                    keycloakAdminService.explainUserAccess(insight.user().id());

            for (GroupRoleGrantResponse assignment : explanation.groupAssignments()) {
                boolean grantsAdmin = assignment.rolesGranted().stream()
                        .anyMatch(role -> role.name().equals("app-admin"));

                if (grantsAdmin) {
                    proposals.add(new RemediationProposalResponse(
                            "REMOVE_USER_FROM_GROUP",
                            explanation.user().id(),
                            explanation.user().username(),
                            assignment.group().id(),
                            assignment.group().name(),
                            insight.reason()
                    ));
                }
            }
        }

        String summary = "I found " + elevatedUsers.size()
                + " elevated-access user"
                + (elevatedUsers.size() == 1 ? "" : "s")
                + " and generated " + proposals.size()
                + " remediation proposal"
                + (proposals.size() == 1 ? "" : "s") + ".";

        return new AgentRemediationResponse(summary, proposals);
    }
}
