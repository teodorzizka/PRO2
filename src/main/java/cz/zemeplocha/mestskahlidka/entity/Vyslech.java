package cz.zemeplocha.mestskahlidka.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "vyslech")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Vyslech {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDateTime datum;

    @Column(length = 4000)
    private String zapis;

    @ManyToOne(optional = false)
    @JoinColumn(name = "podezrely_id", nullable = false)
    private Podezrely podezrely;

    @ManyToOne
    @JoinColumn(name = "straznik_id")
    private Straznik straznik;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public LocalDateTime getDatum() { return datum; }
    public void setDatum(LocalDateTime datum) { this.datum = datum; }

    public String getZapis() { return zapis; }
    public void setZapis(String zapis) { this.zapis = zapis; }

    public Podezrely getPodezrely() { return podezrely; }
    public void setPodezrely(Podezrely podezrely) { this.podezrely = podezrely; }

    public Straznik getStraznik() { return straznik; }
    public void setStraznik(Straznik straznik) { this.straznik = straznik; }
}


