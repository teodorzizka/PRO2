package cz.zemeplocha.mestskahlidka.service;
import cz.zemeplocha.mestskahlidka.dto.StraznikCreateDto;
import cz.zemeplocha.mestskahlidka.dto.StraznikDto;
import cz.zemeplocha.mestskahlidka.entity.Straznik;
import java.util.List;
import java.util.Optional;

public interface StraznikService {
    List<Straznik> findAll();
    Straznik       findById(Long id);
    Straznik       save(Straznik s);
    void           delete(Long id);
    Straznik       createFromDto(StraznikCreateDto dto);
    List<StraznikDto> findAllDto();
    Optional<Straznik> findByUsername(String username);
}
