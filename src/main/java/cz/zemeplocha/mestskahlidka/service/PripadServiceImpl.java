package cz.zemeplocha.mestskahlidka.service;
import cz.zemeplocha.mestskahlidka.dto.PripadCreateDto;
import cz.zemeplocha.mestskahlidka.dto.PripadDto;
import cz.zemeplocha.mestskahlidka.entity.Pripad;
import cz.zemeplocha.mestskahlidka.entity.Straznik;
import cz.zemeplocha.mestskahlidka.repository.PripadRepository;
import cz.zemeplocha.mestskahlidka.repository.StraznikRepository;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;

@Service
public class PripadServiceImpl implements PripadService {
    private final PripadRepository pripadRepo;
    private final StraznikRepository straznikRepo;
    public PripadServiceImpl(PripadRepository pripadRepo, StraznikRepository straznikRepo) {
        this.pripadRepo = pripadRepo;
        this.straznikRepo = straznikRepo;
    }
    @Override
    public List<PripadDto> findAll() {
        return pripadRepo.findAll().stream()
                .map(p -> new PripadDto(
                        p.getId(),
                        p.getNazev(),
                        p.getPopis(),
                        p.getStraznici().stream().findFirst().orElse(null)
                ))
                .toList();
    }
    @Override
    public Pripad findById(Long id) {
        return pripadRepo.findById(id).orElseThrow();
    }

    @Override
    public Pripad create(PripadCreateDto dto) {
        Pripad p = new Pripad();
        p.setNazev(dto.nazev());
        p.setPopis(dto.popis());
        if (p.getDatumZalozeni() == null) {
            p.setDatumZalozeni(LocalDate.now());
        }
        return pripadRepo.save(p);
    }

    @Override
    public void delete(Long id) {
        pripadRepo.deleteById(id);
    }
    @Override
    public Pripad assignStraznik(Long pripadId, Long straznikId) {
        Pripad p = pripadRepo.findById(pripadId).orElseThrow();
        Straznik s = straznikRepo.findById(straznikId).orElseThrow();

        p.getStraznici().add(s);
        s.getPripady().add(p);
        pripadRepo.save(p);
        straznikRepo.save(s);
        return p;
    }
}
