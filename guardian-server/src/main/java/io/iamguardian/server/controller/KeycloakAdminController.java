package io.iamguardian.server.controller;

import io.iamguardian.server.controller.dto.*;
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

    @GetMapping("/api/admin/keycloak/users/{userId}/roles")
    public List<KeycloakRoleResponse> listUserRoles(@PathVariable String userId) {
        return keycloakAdminService.listUserRoles(userId);
    }

    @GetMapping("/api/admin/access/users/{userId}")
    public UserAccessResponse getUserAccess(@PathVariable String userId) {
        return keycloakAdminService.getUserAccess(userId);
    }

    @GetMapping("/api/admin/access/users")
    public List<UserAccessResponse> listAllUserAccess() {
        return keycloakAdminService.listAllUserAccess();
    }

    @GetMapping("/api/admin/access/admins")
    public List<UserAccessResponse> listAdminUsers() {
        return keycloakAdminService.listAdminUsers();
    }

    @GetMapping("/api/admin/keycloak/groups/{groupId}/roles")
    public List<KeycloakRoleResponse> listGroupRoles(@PathVariable String groupId) {
        return keycloakAdminService.listGroupRoles(groupId);
    }

    @GetMapping("/api/admin/access/users/{userId}/explain")
    public UserAccessExplanationResponse explainUserAccess(@PathVariable String userId) {
        return keycloakAdminService.explainUserAccess(userId);
    }

    @GetMapping("/api/admin/access/insights/elevated-users")
    public List<ElevatedAccessInsightResponse> listElevatedUserInsights() {
        return keycloakAdminService.listElevatedUserInsights();
    }
}