package cz.zemeplocha.mestskahlidka.dto;

import jakarta.validation.constraints.*;


public record StraznikCreateDto(
        @NotBlank String jmeno,
        @NotBlank String rasa,
        @NotBlank String hodnost,
        @Min(1) @Max(150) int vek,
        @NotBlank String username,
        @NotBlank String password,
        String role  // může být null → defaultně ROLE_USER
) {}

