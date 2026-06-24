package io.iamguardian.server.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "iamguardian.keycloak")
public record KeycloakProperties(
        String serverUrl,
        String realm,
        String adminRealm,
        String adminClientId,
        String adminUsername,
        String adminPassword
) {
}
