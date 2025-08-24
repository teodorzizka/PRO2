package cz.zemeplocha.mestskahlidka.service;
import cz.zemeplocha.mestskahlidka.dto.StraznikCreateDto;
import cz.zemeplocha.mestskahlidka.dto.StraznikDto;
import cz.zemeplocha.mestskahlidka.entity.Straznik;
import cz.zemeplocha.mestskahlidka.repository.StraznikRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class StraznikServiceImpl implements StraznikService {

    private final StraznikRepository repo;
    private final PasswordEncoder    encoder;

    public StraznikServiceImpl(StraznikRepository repo, PasswordEncoder encoder) {
        this.repo    = repo;
        this.encoder = encoder;
    }

    //CRUD
    @Override public List<Straznik> findAll()              { return repo.findAll(); }
    @Override public Straznik findById(Long id)            { return repo.findById(id).orElseThrow(); }

    @Override public Straznik save(Straznik s) {
        if (s.getPassword() != null && !s.getPassword().startsWith("$2a$")) {
            s.setPassword(encoder.encode(s.getPassword()));
        }
        if (s.getRole() == null || s.getRole().isBlank()) {
            s.setRole("ROLE_USER");
        } else if (!s.getRole().startsWith("ROLE_")) {
            s.setRole("ROLE_" + s.getRole().toUpperCase());
        }
        return repo.save(s);
    }

    @Override public void delete(Long id)                  { repo.deleteById(id); }
    @Override
    public Straznik createFromDto(StraznikCreateDto dto) {
        Straznik s = new Straznik();
        s.setJmeno(dto.jmeno());
        s.setRasa(dto.rasa());
        s.setHodnost(dto.hodnost());
        s.setVek(dto.vek());
        s.setUsername(dto.username());
        s.setPassword(encoder.encode(dto.password()));
        s.setRole("ROLE_" + (dto.role() == null ? "USER" : dto.role().toUpperCase()));
        return repo.save(s);
    }

    @Override
    public List<StraznikDto> findAllDto() {
        return repo.findAll().stream().map(s -> new StraznikDto(
                s.getId(), s.getJmeno(), s.getRasa(), s.getHodnost(),
                s.getVek(), s.getUsername(), s.getRole()
        )).toList();
    }

    @Override
    public Optional<Straznik> findByUsername(String username) {
        return repo.findByUsername(username);
    }
}
