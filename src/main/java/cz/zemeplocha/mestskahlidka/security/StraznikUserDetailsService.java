package cz.zemeplocha.mestskahlidka.security;

import cz.zemeplocha.mestskahlidka.entity.Straznik;
import cz.zemeplocha.mestskahlidka.repository.StraznikRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class StraznikUserDetailsService implements UserDetailsService {

    private final StraznikRepository repo;

    public StraznikUserDetailsService(StraznikRepository repo) {
        this.repo = repo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Straznik s = repo.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Uživatel nenalezen: " + username));

        String authority = s.getRole().startsWith("ROLE_")
                ? s.getRole()
                : "ROLE_" + s.getRole().toUpperCase();

        return new User(
                s.getUsername(),
                s.getPassword(),
                Collections.singletonList(new SimpleGrantedAuthority(authority))
        );
    }
}
