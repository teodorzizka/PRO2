package cz.zemeplocha.mestskahlidka.controller;

import cz.zemeplocha.mestskahlidka.entity.Podezrely;
import cz.zemeplocha.mestskahlidka.entity.Straznik;
import cz.zemeplocha.mestskahlidka.entity.Vyslech;
import cz.zemeplocha.mestskahlidka.repository.PodezrelyRepository;
import cz.zemeplocha.mestskahlidka.repository.StraznikRepository;
import cz.zemeplocha.mestskahlidka.repository.VyslechRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/vyslechy")
public class VyslechController {

    private final VyslechRepository vyslechRepo;
    private final PodezrelyRepository podezrelyRepo;
    private final StraznikRepository straznikRepo;

    public VyslechController(VyslechRepository vyslechRepo,
                             PodezrelyRepository podezrelyRepo,
                             StraznikRepository straznikRepo) {
        this.vyslechRepo = vyslechRepo;
        this.podezrelyRepo = podezrelyRepo;
        this.straznikRepo = straznikRepo;
    }

    @GetMapping
    public List<Vyslech> getAll() {
        return vyslechRepo.findAll();
    }

    @PostMapping
    public Vyslech create(@RequestParam Long podezrelyId,
                          @RequestParam(required = false) String zapis,
                          Authentication auth) {
        Podezrely p = podezrelyRepo.findById(podezrelyId).orElseThrow();

        Straznik s = null;
        if (auth != null && auth.getName() != null) {
            s = straznikRepo.findByUsername(auth.getName()).orElse(null);
        }

        Vyslech v = new Vyslech();
        v.setDatum(LocalDateTime.now());   // LocalDateTime, ne LocalDate!
        v.setZapis(zapis);
        v.setPodezrely(p);
        v.setStraznik(s);

        return vyslechRepo.save(v);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void delete(@PathVariable Long id) {
        vyslechRepo.deleteById(id);
    }
}

