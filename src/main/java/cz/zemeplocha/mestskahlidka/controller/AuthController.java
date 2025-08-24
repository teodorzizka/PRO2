package cz.zemeplocha.mestskahlidka.controller;

import cz.zemeplocha.mestskahlidka.dto.RegisterDto;
import cz.zemeplocha.mestskahlidka.entity.Straznik;
import cz.zemeplocha.mestskahlidka.repository.StraznikRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class AuthController {

    private final StraznikRepository straznikRepository;
    private final PasswordEncoder passwordEncoder;


    public AuthController(StraznikRepository straznikRepository, PasswordEncoder passwordEncoder) {
        this.straznikRepository = straznikRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // Registrace nového strážníka
    @PostMapping("/register")
    public ResponseEntity<String> register(@Valid @RequestBody RegisterDto dto) {

        // Kontrola, jestli uživatel existuje
        if (straznikRepository.findByUsername(dto.getUsername()).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("Uživatel s tímto jménem už existuje.");
        }

        // Vytvoření nového strážníka
        Straznik s = new Straznik();
        s.setUsername(dto.getUsername());
        s.setPassword(passwordEncoder.encode(dto.getPassword()));
        s.setJmeno(dto.getJmeno());
        s.setRasa(dto.getRasa());
        s.setHodnost(dto.getHodnost());
        s.setVek(dto.getVek());
        s.setRole("USER");

        // Uložení
        straznikRepository.save(s);
        return ResponseEntity.ok("Registrace proběhla úspěšně.");
    }
}
