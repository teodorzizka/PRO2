package cz.zemeplocha.mestskahlidka.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;

@Entity
@Table(name = "podezrely")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Podezrely {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String jmeno;

    private Integer vek;

    @Column(length = 1000)
    private String popis;
    @ManyToOne
    @JoinColumn(name = "pripad_id")
    private Pripad pripad;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getJmeno() { return jmeno; }
    public void setJmeno(String jmeno) { this.jmeno = jmeno; }

    public Integer getVek() { return vek; }
    public void setVek(Integer vek) { this.vek = vek; }

    public String getPopis() { return popis; }
    public void setPopis(String popis) { this.popis = popis; }

    public Pripad getPripad() { return pripad; }
    public void setPripad(Pripad pripad) { this.pripad = pripad; }
}
