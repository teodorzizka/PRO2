package cz.zemeplocha.mestskahlidka.service;
import cz.zemeplocha.mestskahlidka.entity.Podezrely;
import cz.zemeplocha.mestskahlidka.repository.PodezrelyRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class PodezrelyServiceImpl implements PodezrelyService {

    private final PodezrelyRepository repo;
    public PodezrelyServiceImpl(PodezrelyRepository repo) {
        this.repo = repo;
    }

    @Override
    public List<Podezrely> findAll() {
        return repo.findAll();
    }
    @Override
    public Podezrely findById(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Podezřelý nenalezen: " + id));
    }

    @Override
    public Podezrely save(Podezrely p) {
        return repo.save(p);
    }
    @Override
    public void delete(Long id) {
        repo.deleteById(id);
    }
}
