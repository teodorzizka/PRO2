package cz.zemeplocha.mestskahlidka.dto;

import cz.zemeplocha.mestskahlidka.entity.Pripad;

import java.util.List;
public class StraznikDto {

    private Long   id;
    private String jmeno;
    private String rasa;
    private String hodnost;
    private int    vek;
    private String username;
    private String role;

    private List<Pripad> pripady;   // může být null



    public StraznikDto() { }

    public StraznikDto(Long id,
                       String jmeno,
                       String rasa,
                       String hodnost,
                       int vek,
                       String username,
                       String role)
    {
        this.id       = id;
        this.jmeno    = jmeno;
        this.rasa     = rasa;
        this.hodnost  = hodnost;
        this.vek      = vek;
        this.username = username;
        this.role     = role;
    }

    public StraznikDto(Long id,
                       String jmeno,
                       String rasa,
                       String hodnost,
                       int vek,
                       String username,
                       String role,
                       List<Pripad> pripady)
    {
        this(id, jmeno, rasa, hodnost, vek, username, role);
        this.pripady = pripady;
    }


    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getJmeno() { return jmeno; }
    public void setJmeno(String jmeno) { this.jmeno = jmeno; }

    public String getRasa() { return rasa; }
    public void setRasa(String rasa) { this.rasa = rasa; }

    public String getHodnost() { return hodnost; }
    public void setHodnost(String hodnost) { this.hodnost = hodnost; }

    public int getVek() { return vek; }
    public void setVek(int vek) { this.vek = vek; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public List<Pripad> getPripady() { return pripady; }
    public void setPripady(List<Pripad> pripady) { this.pripady = pripady; }
}