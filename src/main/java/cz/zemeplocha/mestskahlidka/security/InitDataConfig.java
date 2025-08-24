package cz.zemeplocha.mestskahlidka.security;
import cz.zemeplocha.mestskahlidka.entity.Straznik;
import cz.zemeplocha.mestskahlidka.repository.StraznikRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class InitDataConfig {

    @Bean
    CommandLineRunner runner(StraznikRepository repo, PasswordEncoder encoder) {
        return args -> {
            if (repo.findByUsername("admin").isEmpty()) {
                Straznik s = new Straznik();
                s.setUsername("admin");
                s.setPassword(encoder.encode("heslo123"));
                s.setJmeno("Velitel");
                s.setRasa("Člověk");
                s.setHodnost("Velitel");
                s.setVek(45);
                s.setRole("ROLE_ADMIN");
                repo.save(s);
            }
        };
    }
}