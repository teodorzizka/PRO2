package cz.zemeplocha.mestskahlidka.service;
import cz.zemeplocha.mestskahlidka.entity.Vyslech;
import java.time.LocalDateTime;
import java.util.List;

public interface VyslechService {
    List<Vyslech> findAll();
    Vyslech findById(Long id);
    Vyslech create(Long podezrelyId,
                   Long pripadId,
                   Long straznikId,
                   String zapis,
                   LocalDateTime datum);

    void delete(Long id);
    List<Vyslech> findByPripad(Long pripadId);


    List<Vyslech> findByPodezrely(Long podezrelyId);
}

