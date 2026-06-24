package io.iamguardian.server.service;

import io.iamguardian.server.config.KeycloakProperties;
import io.iamguardian.server.controller.dto.KeycloakGroupResponse;
import io.iamguardian.server.controller.dto.KeycloakUserResponse;
import org.keycloak.admin.client.Keycloak;
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
}