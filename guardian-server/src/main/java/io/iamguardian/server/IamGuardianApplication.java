package io.iamguardian.server;

import io.iamguardian.server.keycloak.KeycloakProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(KeycloakProperties.class)
public class IamGuardianApplication {

    public static void main(String[] args) {
        SpringApplication.run(IamGuardianApplication.class, args);
    }

}
