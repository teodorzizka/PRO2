package cz.zemeplocha.mestskahlidka.repository;
import cz.zemeplocha.mestskahlidka.entity.Straznik;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class StraznikRepositoryTest {
    @Autowired
    private StraznikRepository repository;
    @Test
    void saveAndFindById() {
        Straznik s = new Straznik();
        s.setJmeno("Karotka");
        s.setHodnost("kapitán");
        s.setVek(30);
        s.setRasa("trpaslík");
        Straznik saved = repository.save(s);
        Optional<Straznik> found = repository.findById(saved.getId());
        assertThat(found).isPresent();
        assertThat(found.get().getJmeno()).isEqualTo("Karotka");
    }
}
