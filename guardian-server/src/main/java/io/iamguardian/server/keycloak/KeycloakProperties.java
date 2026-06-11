package io.iamguardian.server.keycloak;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "iamguardian.keycloak")
public record KeycloakProperties (
        String baseUrl,
        String realm,
        String adminRealm,
        String clientId,
        String username,
        String password
) {
}
