package controller;

import modell.Termin;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import java.text.SimpleDateFormat;

@ManagedBean
@SessionScoped

public class EditBean {
    @Inject
    private KalenderBean kalenderCDI;

    private boolean added = false;
    private String publicKey;
    private String privateKey;

    public EditBean(){

    }
    public void resetAktuellerTermin(){
        kalenderCDI.getKalender().setAktuellerTermin(new Termin());
    }

    public void pageLoaded(){
        added = false;

        publicKey = kalenderCDI.getKalender().getAktuellerTermin().getPublicKey();
        privateKey = kalenderCDI.getKalender().getAktuellerTermin().getPrivateKey();
    }

    public String removeTermin(){
        kalenderCDI.getKalender().getAktuellerTermin().setPublicKey(publicKey);
        kalenderCDI.getKalender().getAktuellerTermin().setPrivateKey(privateKey);
        kalenderCDI.getKalender().removeTermin();

        return "/index.xhtml";
    }
    public String changeTermin(){
        kalenderCDI.getKalender().getAktuellerTermin().setPublicKey(publicKey);
        kalenderCDI.getKalender().getAktuellerTermin().setPrivateKey(privateKey);

        if(!added){
            Termin aktuellerTermin = kalenderCDI.getKalender().getAktuellerTermin();
            for(Termin termin:kalenderCDI.getKalender().getGesetzteTermine()){
                if(termin.getZimmernummer()==aktuellerTermin.getZimmernummer()){
                    //Wenn die Zeiten mit anderen Zeiten überlappen
                    if(aktuellerTermin.getVon().before(termin.getBis()) && aktuellerTermin.getBis().after(termin.getVon())){
                        if(!aktuellerTermin.getPrivateKey().equals(termin.getPrivateKey())){
                            SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
                            SimpleDateFormat formatterDate = new SimpleDateFormat("dd.MM.yyyy");
                            String formattedVon = formatter.format(termin.getVon());
                            String formattedBis = formatter.format(termin.getBis());
                            String formattedDate = formatterDate.format(termin.getDatum());

                            FacesMessage message = new FacesMessage("Zimmer "+termin.getZimmernummer()+" ist bereits zwischen "+formattedVon+" und "+formattedBis+" am " + formattedDate+ " reserviert.");
                            FacesContext context = FacesContext.getCurrentInstance();
                            context.addMessage("", message);
                            return null;
                        }
                    }
                }
            }
            if(aktuellerTermin.getVon().after(aktuellerTermin.getBis())){
                FacesMessage message = new FacesMessage("'Von' kann nicht nach 'Bis' sein");
                FacesContext context = FacesContext.getCurrentInstance();
                context.addMessage("", message);
                return null;
            }
            kalenderCDI.getKalender().editTermin();

            added = true;
        }

        return "/index.xhtml";
    }

    public KalenderBean getKalenderCDI() {
        return kalenderCDI;
    }

    public void setKalenderCDI(KalenderBean kalenderCDI) {
        this.kalenderCDI = kalenderCDI;
    }
}
