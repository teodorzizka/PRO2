package cz.zemeplocha.mestskahlidka.dto;

import jakarta.validation.constraints.NotBlank;

public record PripadCreateDto(
        @NotBlank String nazev,
        @NotBlank String popis
) { }

