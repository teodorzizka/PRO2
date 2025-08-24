package cz.zemeplocha.mestskahlidka.dto;
import cz.zemeplocha.mestskahlidka.entity.Straznik;
import java.util.List;
public class StraznikProfilDto {

    public Long id;
    public String jmeno;
    public String rasa;
    public String hodnost;
    public int vek;
    public String username;
    public List<String> pripady;   // např. „krádež kola (#3)“

    public StraznikProfilDto(Straznik s) {
        this.id       = s.getId();
        this.jmeno    = s.getJmeno();
        this.rasa     = s.getRasa();
        this.hodnost  = s.getHodnost();
        this.vek      = s.getVek();
        this.username = s.getUsername();
        this.pripady  = s.getPripady().stream()
                .map(p -> p.getNazev() + " (#" + p.getId() + ")")
                .toList();
    }
}
