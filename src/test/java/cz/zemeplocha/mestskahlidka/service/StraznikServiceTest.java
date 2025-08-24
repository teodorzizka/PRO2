package cz.zemeplocha.mestskahlidka.service;
import cz.zemeplocha.mestskahlidka.entity.Straznik;
import cz.zemeplocha.mestskahlidka.repository.StraznikRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
@ExtendWith(MockitoExtension.class)
class StraznikServiceTest {
    @Mock
    private StraznikRepository straznikRepository;


    @Test
    void findById_returnsOptionalWithEntity() {
        Straznik s = new Straznik();
        s.setId(1L);
        s.setJmeno("Kapitán Elánius");
        when(straznikRepository.findById(1L)).thenReturn(Optional.of(s));
        Optional<Straznik> result = straznikRepository.findById(1L);
        assertThat(result).isPresent();
        assertThat(result.get().getId()).isEqualTo(1L);
        assertThat(result.get().getJmeno()).isEqualTo("Kapitán Elánius");
    }
}
