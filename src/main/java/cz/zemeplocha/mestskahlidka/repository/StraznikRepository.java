package cz.zemeplocha.mestskahlidka.repository;
import cz.zemeplocha.mestskahlidka.entity.Straznik;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
public interface StraznikRepository extends JpaRepository<Straznik, Long> {
    Optional<Straznik> findByUsername(String username);
}


