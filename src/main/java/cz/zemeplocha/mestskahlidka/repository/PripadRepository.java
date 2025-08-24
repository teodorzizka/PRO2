package cz.zemeplocha.mestskahlidka.repository;

import cz.zemeplocha.mestskahlidka.entity.Pripad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PripadRepository extends JpaRepository<Pripad, Long> {



    @Query("select distinct p from Pripad p left join fetch p.straznici")
    List<Pripad> findAllWithStraznik();
}

