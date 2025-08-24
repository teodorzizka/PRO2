package cz.zemeplocha.mestskahlidka.controller;

import cz.zemeplocha.mestskahlidka.entity.Podezrely;
import cz.zemeplocha.mestskahlidka.entity.Pripad;
import cz.zemeplocha.mestskahlidka.repository.PodezrelyRepository;
import cz.zemeplocha.mestskahlidka.repository.PripadRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/podezreli")
@CrossOrigin(origins = "*") // pro jistotu kvůli FE
public class PodezrelyController {

    private final PodezrelyRepository podezrelyRepo;
    private final PripadRepository pripadRepo;

    public PodezrelyController(PodezrelyRepository podezrelyRepo, PripadRepository pripadRepo) {
        this.podezrelyRepo = podezrelyRepo;
        this.pripadRepo = pripadRepo;
    }

    @GetMapping
    public List<Podezrely> getAll() {
        return podezrelyRepo.findAll();
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN','USER')") // když chceš jen admina, dej hasRole('ADMIN')
    public Podezrely create(@RequestBody(required = false) Podezrely body,
                            @RequestParam(required = false) String jmeno,
                            @RequestParam(required = false) Integer vek,
                            @RequestParam(required = false) String popis) {
        Podezrely p = (body != null) ? body : new Podezrely();
        if (body == null) {
            p.setJmeno(jmeno);
            p.setVek(vek);
            p.setPopis(popis);
        }
        return podezrelyRepo.save(p);
    }


    @PutMapping("/{id}/pripad/{pid}")
    @PreAuthorize("hasAnyRole('ADMIN','USER')") // případně jen ADMIN
    public Podezrely assignToPripadPath(@PathVariable Long id, @PathVariable Long pid) {
        Podezrely p = podezrelyRepo.findById(id).orElseThrow();
        Pripad pripad = pripadRepo.findById(pid).orElseThrow();
        p.setPripad(pripad);
        return podezrelyRepo.save(p);
    }

    @RequestMapping(path = "/assign", method = { RequestMethod.POST, RequestMethod.PUT })
    @PreAuthorize("hasAnyRole('ADMIN','USER')") // případně jen ADMIN
    public Podezrely assignToPripadParams(@RequestParam("idPodezreleho") Long idPodezreleho,
                                          @RequestParam("idPripadu") Long idPripadu) {
        Podezrely p = podezrelyRepo.findById(idPodezreleho).orElseThrow();
        Pripad pripad = pripadRepo.findById(idPripadu).orElseThrow();
        p.setPripad(pripad);
        return podezrelyRepo.save(p);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void delete(@PathVariable Long id) {
        podezrelyRepo.deleteById(id);
    }
}
