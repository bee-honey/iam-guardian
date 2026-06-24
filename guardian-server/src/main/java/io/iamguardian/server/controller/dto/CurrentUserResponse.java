package io.iamguardian.server.controller.dto;

import java.util.List;

public record CurrentUserResponse(
        String subject,
        String username,
        List<String> roles
) {
}
