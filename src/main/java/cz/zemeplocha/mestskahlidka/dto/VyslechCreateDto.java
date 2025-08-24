package cz.zemeplocha.mestskahlidka.dto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

public record VyslechCreateDto(
        @NotNull  LocalDateTime datum,
        @NotBlank String zapis,
        @NotNull  Long idPodezreleho,
        Long      idStraznika     // volitelné
) { }
