package cz.zemeplocha.mestskahlidka.controller;

import cz.zemeplocha.mestskahlidka.entity.Straznik;
import cz.zemeplocha.mestskahlidka.repository.StraznikRepository;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class WhoAmIController {

    private final StraznikRepository repo;

    public WhoAmIController(StraznikRepository repo) {
        this.repo = repo;
    }

    @GetMapping("/api/whoami")
    public Map<String, Object> whoami(Authentication auth) {
        String username = auth.getName();  // „elanius“
        Straznik s = repo.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Uživatel nenalezen"));

        String role = auth.getAuthorities().stream()
                .findFirst().map(Object::toString).orElse("UNKNOWN");

        return Map.of(
                "id", s.getId(),
                "username", s.getUsername(),
                "jmeno", s.getJmeno(),
                "role", role
        );
    }
}