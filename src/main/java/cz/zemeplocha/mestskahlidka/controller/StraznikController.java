package cz.zemeplocha.mestskahlidka.controller;

import cz.zemeplocha.mestskahlidka.entity.Pripad;
import cz.zemeplocha.mestskahlidka.entity.Straznik;
import cz.zemeplocha.mestskahlidka.repository.PripadRepository;
import cz.zemeplocha.mestskahlidka.repository.StraznikRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/straznici")
@CrossOrigin(origins = "*")
public class StraznikController {

    private final StraznikRepository straznikRepo;
    private final PripadRepository pripadRepo;
    private final PasswordEncoder encoder;

    public StraznikController(StraznikRepository straznikRepo,
                              PripadRepository pripadRepo,
                              PasswordEncoder encoder) {
        this.straznikRepo = straznikRepo;
        this.pripadRepo = pripadRepo;
        this.encoder = encoder;
    }

    @GetMapping("/profil")
    public Straznik getMyProfile(Principal principal) {
        return straznikRepo.findByUsername(principal.getName()).orElseThrow();
    }

    @GetMapping
    public List<Straznik> getAll() {
        return straznikRepo.findAll();
    }

    @GetMapping("/{id}")
    public Straznik getById(@PathVariable Long id) {
        return straznikRepo.findById(id).orElseThrow();
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void delete(@PathVariable Long id) {
        straznikRepo.deleteById(id);
    }


    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public Straznik create(@RequestBody(required = false) Straznik body,
                           @RequestParam(required = false) String jmeno,
                           @RequestParam(required = false) String hodnost,
                           @RequestParam(required = false) Integer vek,
                           @RequestParam(required = false) String rasa,
                           @RequestParam(required = false) String username,
                           @RequestParam(required = false) String password,
                           @RequestParam(required = false) String role) {

        final Straznik s = (body != null) ? body : new Straznik();

        if (body == null) {
            s.setJmeno(jmeno);
            s.setHodnost(hodnost);
            s.setVek(vek);
            s.setRasa(rasa);
            s.setUsername(username);
            s.setPassword(password);
            s.setRole(role);
        }

        if (s.getVek() != null && s.getVek() <= 0) {
            s.setVek(null);
        }

        if (s.getRole() == null || s.getRole().isBlank()) {
            s.setRole("ROLE_USER");
        } else if (!s.getRole().startsWith("ROLE_")) {
            s.setRole("ROLE_" + s.getRole().toUpperCase());
        }

        if (s.getPassword() != null && !s.getPassword().isBlank()
                && !s.getPassword().startsWith("$2a$")) {
            s.setPassword(encoder.encode(s.getPassword()));
        }

        return straznikRepo.save(s);
    }


    @PutMapping("/{id}/straznik/{straznikId}") // nechávám kompatibilní s tvým FE assignStraznik()
    @PreAuthorize("hasRole('ADMIN')")
    public Pripad assignStraznikToPripad(@PathVariable Long id, @PathVariable Long straznikId) {
        final Pripad p = pripadRepo.findById(id).orElseThrow();
        final Straznik s = straznikRepo.findById(straznikId).orElseThrow();

        s.addPripad(p);
        straznikRepo.save(s);
        return p;
    }
}
