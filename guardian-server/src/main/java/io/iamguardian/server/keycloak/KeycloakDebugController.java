package io.iamguardian.server.keycloak;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
public class KeycloakDebugController {
    private final KeycloakAdminClient keycloakAdminClient;

    public KeycloakDebugController(KeycloakAdminClient keycloakAdminClient) {
        this.keycloakAdminClient = keycloakAdminClient;
    }

    @GetMapping("/api/debug/keycloak/groups")
    public List<Map<String, Object>> groups() {
        List<Map<String, Object>> groups = keycloakAdminClient.getGroups();
        List<Map<String, Object>> result = new ArrayList<>();
        for (Map<String, Object> group : groups) {
            String groupId = (String) group.get("id");
            Map<String, Object> row = new LinkedHashMap<>();
            row.put("id", groupId);
            row.put("name", group.get("name"));
            row.put("roleMappings", keycloakAdminClient.getGroupRoleMappings(groupId));
            result.add(row);
        }
        return result;
    }
}
