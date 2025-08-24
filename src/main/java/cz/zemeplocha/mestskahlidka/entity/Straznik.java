package cz.zemeplocha.mestskahlidka.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;

import java.util.*;

@Entity
@Table(
        name = "straznik",
        indexes = {
                @Index(name = "ux_straznik_username", columnList = "username", unique = true)
        }
)
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Straznik {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String jmeno;

    private String rasa;
    private String hodnost;
    private Integer vek;
    @Column(nullable = true, unique = true, length = 80)
    private String username;
    @JsonIgnore
    @Column(nullable = true, length = 200)
    private String password;
    @Column(nullable = true, length = 40)
    private String role;

    @ManyToMany
    @JoinTable(
            name = "straznik_pripad",
            joinColumns = @JoinColumn(name = "straznik_id"),
            inverseJoinColumns = @JoinColumn(name = "pripad_id")
    )
    private Set<Pripad> pripady = new HashSet<>();

    @OneToMany(mappedBy = "straznik")
    private List<Vyslech> vyslechy = new ArrayList<>();

    public void addPripad(Pripad p) {
        if (p == null) return;
        this.pripady.add(p);
        try {
            if (p.getStraznici() != null) {
                p.getStraznici().add(this);
            }
        } catch (Exception ignored) {}
    }

    public void removePripad(Pripad p) {
        if (p == null) return;
        this.pripady.remove(p);
        try {
            if (p.getStraznici() != null) {
                p.getStraznici().remove(this);
            }
        } catch (Exception ignored) {}
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getJmeno() { return jmeno; }
    public void setJmeno(String jmeno) { this.jmeno = jmeno; }

    public String getRasa() { return rasa; }
    public void setRasa(String rasa) { this.rasa = rasa; }

    public String getHodnost() { return hodnost; }
    public void setHodnost(String hodnost) { this.hodnost = hodnost; }

    public Integer getVek() { return vek; }
    public void setVek(Integer vek) { this.vek = vek; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public Set<Pripad> getPripady() { return pripady; }
    public void setPripady(Set<Pripad> pripady) { this.pripady = (pripady != null ? pripady : new HashSet<>()); }

    public List<Vyslech> getVyslechy() { return vyslechy; }
    public void setVyslechy(List<Vyslech> vyslechy) { this.vyslechy = (vyslechy != null ? vyslechy : new ArrayList<>()); }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Straznik other)) return false;
        return id != null && id.equals(other.id);
    }

    @Override
    public int hashCode() { return Objects.hashCode(id); }
}
