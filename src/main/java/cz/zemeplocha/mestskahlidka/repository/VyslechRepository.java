package cz.zemeplocha.mestskahlidka.repository;

import cz.zemeplocha.mestskahlidka.entity.Vyslech;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface VyslechRepository extends JpaRepository<Vyslech, Long> {

    List<Vyslech> findByPodezrely_Id(Long podezrelyId);

    // Aktuální model: Podezrely má ManyToOne na Pripad => join přes pd.pripad
    @Query("""
           select v
           from Vyslech v
             join v.podezrely pd
             join pd.pripad pr
           where pr.id = :pripadId
           """)
    List<Vyslech> findByPripadId(@Param("pripadId") Long pripadId);
}


