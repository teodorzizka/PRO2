package cz.zemeplocha.mestskahlidka.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.*;

@Entity
@Table(name = "pripad")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Pripad {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nazev;

    @Column(length = 2000)
    private String popis;

    private LocalDate datumZalozeni;
    @ManyToMany(mappedBy = "pripady")
    private Set<Straznik> straznici = new HashSet<>();

    @OneToMany(mappedBy = "pripad")
    private List<Podezrely> podezreli = new ArrayList<>();

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNazev() { return nazev; }
    public void setNazev(String nazev) { this.nazev = nazev; }

    public String getPopis() { return popis; }
    public void setPopis(String popis) { this.popis = popis; }

    public LocalDate getDatumZalozeni() { return datumZalozeni; }
    public void setDatumZalozeni(LocalDate datumZalozeni) { this.datumZalozeni = datumZalozeni; }

    public Set<Straznik> getStraznici() { return straznici; }
    public void setStraznici(Set<Straznik> straznici) { this.straznici = straznici; }

    public List<Podezrely> getPodezreli() { return podezreli; }
    public void setPodezreli(List<Podezrely> podezreli) { this.podezreli = podezreli; }
}
