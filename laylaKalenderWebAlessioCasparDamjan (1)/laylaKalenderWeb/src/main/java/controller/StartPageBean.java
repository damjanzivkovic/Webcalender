package controller;

import modell.Termin;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.inject.Inject;

@ManagedBean
@SessionScoped

public class StartPageBean {
    @Inject
    private KalenderBean kalenderCDI;
    private String publicKeyInput;
    private String privateKeyInput;

    public StartPageBean(){

    }
    public void resetAktuellerTermin(){
        kalenderCDI.getKalender().setAktuellerTermin(new Termin());
    }
    public String checkPublicKey(){
        for(Termin termin:kalenderCDI.getKalender().getGesetzteTermine()){
            if(termin.getPublicKey().equals(publicKeyInput)){
                kalenderCDI.getKalender().setAktuellerTermin(termin);
                return "/terminAnsehen.xhtml";
            }
        }

        return null;
    }
    public String checkPrivateKey(){
        for(Termin termin:kalenderCDI.getKalender().getGesetzteTermine()){
            if(termin.getPrivateKey().equals(privateKeyInput)){
                kalenderCDI.getKalender().setAktuellerTermin(termin);
                return "/terminBearbeiten.xhtml";
            }
        }

        return null;
    }

    public KalenderBean getKalenderCDI() {
        return kalenderCDI;
    }

    public void setKalenderCDI(KalenderBean kalenderCDI) {
        this.kalenderCDI = kalenderCDI;
    }

    public String getPublicKeyInput() {
        return publicKeyInput;
    }

    public void setPublicKeyInput(String publicKeyInput) {
        this.publicKeyInput = publicKeyInput;
    }

    public String getPrivateKeyInput() {
        return privateKeyInput;
    }

    public void setPrivateKeyInput(String privateKeyInput) {
        this.privateKeyInput = privateKeyInput;
    }
}
