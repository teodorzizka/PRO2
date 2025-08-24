package cz.zemeplocha.mestskahlidka.service;

import cz.zemeplocha.mestskahlidka.dto.PripadCreateDto;
import cz.zemeplocha.mestskahlidka.dto.PripadDto;
import cz.zemeplocha.mestskahlidka.entity.Pripad;

import java.util.List;

public interface PripadService {
    List<PripadDto> findAll();
    Pripad findById(Long id);
    Pripad create(PripadCreateDto dto);
    void delete(Long id);
    Pripad assignStraznik(Long pripadId, Long straznikId);
}


