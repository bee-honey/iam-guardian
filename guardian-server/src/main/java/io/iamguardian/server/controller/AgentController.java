package io.iamguardian.server.controller;

import io.iamguardian.server.controller.dto.ai.AgentInvestigationRequest;
import io.iamguardian.server.controller.dto.ai.AgentInvestigationResponse;
import io.iamguardian.server.service.AccessInvestigationAgent;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AgentController {

    private final AccessInvestigationAgent accessInvestigationAgent;

    public AgentController(AccessInvestigationAgent accessInvestigationAgent) {
        this.accessInvestigationAgent = accessInvestigationAgent;
    }

    @PostMapping("/api/agent/investigate-access")
    public AgentInvestigationResponse investigateAccess(@RequestBody AgentInvestigationRequest request) {
        return accessInvestigationAgent.investigate(request);
    }
}
