package io.iamguardian.server.controller;

import io.iamguardian.server.controller.dto.CurrentUserResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {

    @GetMapping("/api/me")
    public CurrentUserResponse me(Authentication authentication) {
        JwtAuthenticationToken jwtAuth = (JwtAuthenticationToken) authentication;

        String subject = jwtAuth.getToken().getSubject();
        String username = jwtAuth.getToken().getClaimAsString("preferred_username");

        List<String> roles = jwtAuth.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .filter(authority -> authority.equals("ROLE_app-admin")
                        || authority.equals("ROLE_app-user"))
                .map(authority -> authority.substring("ROLE_".length()))
                .sorted()
                .toList();

        return new CurrentUserResponse(subject, username, roles);
    }
}
