package modell;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class Termin {
    private Date datum;
    private Date von;
    private Date bis;
    private int zimmernummer;
    private String bemerkung;
    private List<String> teilnehmer;
    private String teilnehmerAsString;
    private String publicKey;
    private String privateKey;
    private String gruender;


    public Termin(){

    }

    public Date getDatum() {
        return datum;
    }

    public void setDatum(Date datum) {
        this.datum = datum;
    }

    public Date getVon() {
        return von;
    }

    public void setVon(Date von) {
        this.von = von;
    }

    public Date getBis() {
        return bis;
    }

    public void setBis(Date bis) {
        this.bis = bis;
    }

    public int getZimmernummer() {
        return zimmernummer;
    }

    public void setZimmernummer(int zimmernummer) {
        this.zimmernummer = zimmernummer;
    }

    public String getBemerkung() {
        return bemerkung;
    }

    public void setBemerkung(String bemerkung) {
        this.bemerkung = bemerkung;
    }

    public List<String> getTeilnehmer() {
        return teilnehmer;
    }
    public void setTeilnehmerAsString(String teilnehmerString){
        this.teilnehmerAsString=teilnehmerString;
        teilnehmer = Arrays.asList(teilnehmerString.split(","));
    }
    public String getTeilnehmerAsString(){return teilnehmerAsString;}

    public void setTeilnehmer(List<String> teilnehmer) {
        this.teilnehmer = teilnehmer;
    }

    public String getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }


    public String getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
    }

    public String getGruender() {
        return gruender;
    }

    public void setGruender(String gruender) {
        this.gruender = gruender;
    }
}
