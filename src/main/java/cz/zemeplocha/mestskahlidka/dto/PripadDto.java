package cz.zemeplocha.mestskahlidka.dto;

import cz.zemeplocha.mestskahlidka.entity.Straznik;

public record PripadDto(
        Long id,
        String nazev,
        String popis,
        Straznik straznik
) {}



