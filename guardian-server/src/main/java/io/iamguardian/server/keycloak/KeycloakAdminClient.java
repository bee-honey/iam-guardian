package io.iamguardian.server.keycloak;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import java.util.List;
import java.util.Map;

@Component
public class KeycloakAdminClient {
    private final KeycloakProperties properties;
    private final RestClient restClient;

    public KeycloakAdminClient(KeycloakProperties properties) {
        this.properties = properties;
        this.restClient = RestClient.builder()
                .baseUrl(properties.baseUrl())
                .build();
    }

    public List<Map<String, Object>> getGroups() {
        String token = getAccessToken();

        return restClient.get()
                .uri("/admin/realms/{realm}/groups", properties.realm())
                .headers(headers -> headers.setBearerAuth(token))
                .retrieve()
                .body(List.class);
    }

    public Map<String, Object> getGroupRoleMappings(String groupId) {
        String token = getAccessToken();
        return restClient.get()
                .uri("/admin/realms/{realm}/groups/{groupId}/role-mappings", properties.realm(), groupId)
                .headers(headers -> headers.setBearerAuth(token))
                .retrieve()
                .body(Map.class);
    }

    private String getAccessToken() {
        Map<String, String> response = restClient.post()
                .uri("/realms/{realm}/protocol/openid-connect/token", properties.adminRealm())
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body("client_id=" + properties.clientId()
                        + "&username=" + properties.username()
                        + "&password=" + properties.password()
                        + "&grant_type=password")
                .retrieve()
                .body(Map.class);
        return response.get("access_token");

    }
}
