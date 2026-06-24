package io.iamguardian.server.controller;

import io.iamguardian.server.controller.dto.ai.AgentInvestigationRequest;
import io.iamguardian.server.controller.dto.ai.AgentInvestigationResponse;
import io.iamguardian.server.controller.dto.ai.AgentRemediationResponse;
import io.iamguardian.server.service.AccessInvestigationAgent;
import io.iamguardian.server.service.AccessRemediationAgent;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AgentController {

    private final AccessInvestigationAgent accessInvestigationAgent;
    private final AccessRemediationAgent accessRemediationAgent;

    public AgentController(AccessInvestigationAgent accessInvestigationAgent, AccessRemediationAgent accessRemediationAgent) {
        this.accessInvestigationAgent = accessInvestigationAgent;
        this.accessRemediationAgent = accessRemediationAgent;
    }

    @PostMapping("/api/agent/investigate-access")
    public AgentInvestigationResponse investigateAccess(@RequestBody AgentInvestigationRequest request) {
        return accessInvestigationAgent.investigate(request);
    }

    @PostMapping("/api/agent/propose-remediation")
    public AgentRemediationResponse proposeRemediation(@RequestBody AgentInvestigationRequest request) {
        return accessRemediationAgent.proposeRemediation(request.question());
    }

}
