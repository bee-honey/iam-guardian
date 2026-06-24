package io.iamguardian.server.controller.check;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthTestController {

    @GetMapping("/api/public/ping")
    public String publicPing() {
        return "public ok";
    }

    @GetMapping("/api/user/ping")
    public String userPing(Authentication authentication) {
        return "user ok: " + authentication.getName();
    }

    @GetMapping("/api/admin/ping")
    public String adminPing(Authentication authentication) {
        return "admin ok: " + authentication.getName();
    }
}
