package io.iamguardian.server.controllers.dto;

import java.util.List;

public record CurrentUserResponse(
        String subject,
        String username,
        List<String> roles
) {
}
