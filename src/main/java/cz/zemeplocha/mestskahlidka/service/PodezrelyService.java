package cz.zemeplocha.mestskahlidka.service;
import cz.zemeplocha.mestskahlidka.entity.Podezrely;
import java.util.List;

public interface PodezrelyService {
    List<Podezrely> findAll();
    Podezrely findById(Long id);
    Podezrely save(Podezrely p);
    void delete(Long id);
}
