package io.iamguardian.server.controller;

import io.iamguardian.server.controller.dto.KeycloakGroupResponse;
import io.iamguardian.server.controller.dto.KeycloakUserResponse;
import io.iamguardian.server.service.KeycloakAdminService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class KeycloakAdminController {

    private final KeycloakAdminService keycloakAdminService;

    public KeycloakAdminController(KeycloakAdminService keycloakAdminService) {
        this.keycloakAdminService = keycloakAdminService;
    }

    @GetMapping("/api/admin/keycloak/users")
    public List<KeycloakUserResponse> listUsers() {
        return keycloakAdminService.listUsers();
    }

    @GetMapping("/api/admin/keycloak/groups")
    public List<KeycloakGroupResponse> listGroups() {
        return keycloakAdminService.listGroups();
    }

    @GetMapping("/api/admin/keycloak/users/{userId}/groups")
    public List<KeycloakGroupResponse> listUserGroups(@PathVariable String userId) {
        return keycloakAdminService.listUserGroups(userId);
    }
}