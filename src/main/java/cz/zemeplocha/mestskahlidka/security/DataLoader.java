package cz.zemeplocha.mestskahlidka.security;
import cz.zemeplocha.mestskahlidka.entity.Straznik;
import cz.zemeplocha.mestskahlidka.repository.StraznikRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataLoader {
    @Bean
    CommandLineRunner init(StraznikRepository repo, PasswordEncoder encoder) {
        return args -> {
            /* --- ADMIN: elanius --- */
            if (repo.findByUsername("elanius").isEmpty()) {
                Straznik admin = new Straznik();
                admin.setJmeno("Kapitán Elánius");
                admin.setRasa("Člověk");
                admin.setHodnost("VELITEL");
                admin.setVek(45);
                admin.setUsername("elanius");
                admin.setPassword(encoder.encode("1234"));
                admin.setRole("ADMIN"); // ⬅️ SPRÁVNĚ bez prefixu
                repo.save(admin);
                System.out.println("✅ Vložen admin elanius / 1234");
            }

            if (repo.findByUsername("karotka").isEmpty()) {
                Straznik user = new Straznik();
                user.setJmeno("Desátník Karotka");
                user.setRasa("Člověk");
                user.setHodnost("DESATNIK");
                user.setVek(27);
                user.setUsername("karotka");
                user.setPassword(encoder.encode("1234"));
                user.setRole("USER"); // ⬅️ SPRÁVNĚ bez prefixu
                repo.save(user);
                System.out.println("✅ Vložen user karotka / 1234");
            }
        };
    }
}