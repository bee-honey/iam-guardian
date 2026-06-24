package io.iamguardian.server.service;

import io.iamguardian.server.config.KeycloakProperties;
import io.iamguardian.server.controller.dto.keycloak.*;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class KeycloakAdminService {

    private final Keycloak keycloak;
    private final KeycloakProperties properties;

    public KeycloakAdminService(Keycloak keycloak, KeycloakProperties properties) {
        this.keycloak = keycloak;
        this.properties = properties;
    }

    public List<KeycloakUserResponse> listUsers() {
        List<UserRepresentation> users = keycloak.realm(properties.realm())
                .users()
                .list();

        return users.stream()
                .map(user -> new KeycloakUserResponse(
                        user.getId(),
                        user.getUsername(),
                        user.getEmail(),
                        user.isEnabled()
                ))
                .toList();
    }

    public List<KeycloakGroupResponse> listGroups() {
        return keycloak.realm(properties.realm())
                .groups()
                .groups()
                .stream()
                .map(group -> new KeycloakGroupResponse(
                        group.getId(),
                        group.getName(),
                        group.getPath()
                ))
                .toList();
    }

    public List<KeycloakGroupResponse> listUserGroups(String userId) {
        return keycloak.realm(properties.realm())
                .users()
                .get(userId)
                .groups()
                .stream()
                .map(group -> new KeycloakGroupResponse(
                        group.getId(),
                        group.getName(),
                        group.getPath()
                ))
                .toList();
    }

    public List<KeycloakRoleResponse> listUserRoles(String userId) {
        List<RoleRepresentation> roles = keycloak.realm(properties.realm())
                .users()
                .get(userId)
                .roles()
                .realmLevel()
                .listEffective();

        return roles.stream()
                .map(role -> new KeycloakRoleResponse(
                        role.getId(),
                        role.getName(),
                        role.getDescription()
                ))
                .filter(role -> role.name().startsWith("app-"))
                .toList();
    }

    public KeycloakUserResponse getUser(String userId) {
        var user = keycloak.realm(properties.realm())
                .users()
                .get(userId)
                .toRepresentation();

        return new KeycloakUserResponse(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.isEnabled()
        );
    }

    public UserAccessResponse getUserAccess(String userId) {
        return new UserAccessResponse(
                getUser(userId),
                listUserGroups(userId),
                listUserRoles(userId)
        );
    }

    public List<UserAccessResponse> listAllUserAccess() {
        return listUsers().stream()
                .map(user -> getUserAccess(user.id()))
                .toList();
    }

    public List<UserAccessResponse> listAdminUsers() {
        return listAllUserAccess().stream()
                .filter(access -> access.effectiveRoles().stream()
                        .anyMatch(role -> role.name().equals("app-admin")))
                .toList();
    }

    public List<KeycloakRoleResponse> listGroupRoles(String groupId) {
        return keycloak.realm(properties.realm())
                .groups()
                .group(groupId)
                .roles()
                .realmLevel()
                .listAll()
                .stream()
                .map(role -> new KeycloakRoleResponse(
                        role.getId(),
                        role.getName(),
                        role.getDescription()
                ))
                .filter(role -> role.name().startsWith("app-"))
                .toList();
    }

    public UserAccessExplanationResponse explainUserAccess(String userId) {
        KeycloakUserResponse user = getUser(userId);

        List<GroupRoleGrantResponse> groupAssignments = listUserGroups(userId).stream()
                .map(group -> new GroupRoleGrantResponse(
                        group,
                        listGroupRoles(group.id())
                ))
                .toList();

        return new UserAccessExplanationResponse(
                user,
                groupAssignments,
                listUserRoles(userId)
        );
    }

    public List<ElevatedAccessInsightResponse> listElevatedUserInsights() {
        return listAllUserAccess().stream()
                .filter(access -> access.effectiveRoles().stream()
                        .anyMatch(role -> role.name().equals("app-admin")))
                .map(access -> new ElevatedAccessInsightResponse(
                        access.user(),
                        "app-admin",
                        buildAdminReason(access.user().id())
                ))
                .toList();
    }

    private String buildAdminReason(String userId) {
        UserAccessExplanationResponse explanation = explainUserAccess(userId);

        return explanation.groupAssignments().stream()
                .filter(groupAssignment -> groupAssignment.rolesGranted().stream()
                        .anyMatch(role -> role.name().equals("app-admin")))
                .map(groupAssignment -> explanation.user().username()
                        + " has app-admin because group "
                        + groupAssignment.group().name()
                        + " grants app-admin")
                .findFirst()
                .orElse(explanation.user().username()
                        + " has app-admin, but no group-based grant was found");
    }

    public RemediationResponse removeUserFromGroup(RemoveUserFromGroupRequest request) {
        KeycloakUserResponse user = getUser(request.userId());

        KeycloakGroupResponse group = listUserGroups(request.userId()).stream()
                .filter(existingGroup -> existingGroup.id().equals(request.groupId()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("User is not a member of this group"));

        if (request.dryRun()) {
            return new RemediationResponse(
                    "REMOVE_USER_FROM_GROUP",
                    true,
                    "Would remove " + user.username() + " from " + group.name()
            );
        }

        keycloak.realm(properties.realm())
                .users()
                .get(request.userId())
                .leaveGroup(request.groupId());

        return new RemediationResponse(
                "REMOVE_USER_FROM_GROUP",
                false,
                "Removed " + user.username() + " from " + group.name()
        );
    }
}