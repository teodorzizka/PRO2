package cz.zemeplocha.mestskahlidka.dto;

import jakarta.validation.constraints.*;

public class RegisterDto {

    @NotBlank
    private String username;

    @NotBlank
    private String password;

    @NotBlank
    private String jmeno;

    /** Volitelné údaje: */
    private String rasa = "člověk";   // výchozí, pokud klient nepošle
    private String hodnost = "nováček";

    @Min(15) @Max(120)
    private int vek = 25;



    public String getUsername()            { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword()            { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getJmeno()               { return jmeno; }
    public void setJmeno(String jmeno)     { this.jmeno = jmeno; }

    public String getRasa()                { return rasa; }
    public void setRasa(String rasa)       { this.rasa = rasa; }

    public String getHodnost()             { return hodnost; }
    public void setHodnost(String hodnost) { this.hodnost = hodnost; }

    public int getVek()                    { return vek; }
    public void setVek(int vek)            { this.vek = vek; }
}
