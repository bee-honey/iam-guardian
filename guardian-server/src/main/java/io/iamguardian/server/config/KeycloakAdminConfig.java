package io.iamguardian.server.config;

import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class KeycloakAdminConfig {

    @Bean
    public Keycloak keycloakAdminClient(KeycloakProperties properties) {
        return KeycloakBuilder.builder()
                .serverUrl(properties.serverUrl())
                .realm(properties.adminRealm())
                .clientId(properties.adminClientId())
                .username(properties.adminUsername())
                .password(properties.adminPassword())
                .grantType(OAuth2Constants.PASSWORD)
                .build();
    }
}