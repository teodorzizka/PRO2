package cz.zemeplocha.mestskahlidka.controller;

import cz.zemeplocha.mestskahlidka.dto.PripadCreateDto;
import cz.zemeplocha.mestskahlidka.dto.PripadDto;
import cz.zemeplocha.mestskahlidka.entity.Pripad;
import cz.zemeplocha.mestskahlidka.service.PripadService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pripady")
public class PripadController {

    private final PripadService service;

    public PripadController(PripadService service) {
        this.service = service;
    }

    @GetMapping
    public List<PripadDto> getAll() {
        return service.findAll();
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public Pripad create(@RequestBody PripadCreateDto dto) {
        return service.create(dto);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }

    @PutMapping("/{id}/straznik/{sid}")
    @PreAuthorize("hasRole('ADMIN')")
    public Pripad assignStraznik(@PathVariable Long id, @PathVariable Long sid) {
        return service.assignStraznik(id, sid);
    }
}

