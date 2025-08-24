package cz.zemeplocha.mestskahlidka.service;
import cz.zemeplocha.mestskahlidka.entity.Podezrely;
import cz.zemeplocha.mestskahlidka.entity.Straznik;
import cz.zemeplocha.mestskahlidka.entity.Vyslech;
import cz.zemeplocha.mestskahlidka.repository.PodezrelyRepository;
import cz.zemeplocha.mestskahlidka.repository.StraznikRepository;
import cz.zemeplocha.mestskahlidka.repository.VyslechRepository;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class VyslechServiceImpl implements VyslechService {

    private final VyslechRepository vyslechRepo;
    private final PodezrelyRepository podezrelyRepo;
    private final StraznikRepository straznikRepo;

    public VyslechServiceImpl(VyslechRepository vyslechRepo,
                              PodezrelyRepository podezrelyRepo,
                              StraznikRepository straznikRepo) {
        this.vyslechRepo = vyslechRepo;
        this.podezrelyRepo = podezrelyRepo;
        this.straznikRepo = straznikRepo;
    }
    @Override
    public List<Vyslech> findAll() {
        return vyslechRepo.findAll();
    }
    @Override
    public Vyslech findById(Long id) {
        return vyslechRepo.findById(id).orElseThrow();
    }


    @Override
    public Vyslech create(Long podezrelyId,
                          Long pripadId,   // zůstává v signatuře, nevyužíváme přímo
                          Long straznikId,
                          String zapis,
                          LocalDateTime datum) {
        Podezrely pd = podezrelyRepo.findById(podezrelyId).orElseThrow();
        Straznik s = null;
        if (straznikId != null) {
            s = straznikRepo.findById(straznikId).orElse(null);
        }


        Vyslech v = new Vyslech();
        v.setPodezrely(pd);
        v.setStraznik(s);
        v.setZapis(zapis);
        v.setDatum(datum != null ? datum : LocalDateTime.now());
        return vyslechRepo.save(v);
    }
    @Override
    public void delete(Long id) {
        vyslechRepo.deleteById(id);
    }


    @Override
    public List<Vyslech> findByPripad(Long pripadId) {
        return vyslechRepo.findByPripadId(pripadId);
    }

    @Override
    public List<Vyslech> findByPodezrely(Long podezrelyId) {
        return vyslechRepo.findByPodezrely_Id(podezrelyId);
    }
}
