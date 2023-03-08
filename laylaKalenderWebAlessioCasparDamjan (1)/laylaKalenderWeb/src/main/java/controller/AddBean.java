package controller;

import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMultipart;
import modell.Termin;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

@ManagedBean
@SessionScoped

public class AddBean{
    @Inject
    private KalenderBean kalenderCDI;
    private boolean added = false;

    public AddBean(){

    }

    public void pageLoaded(){
        added = false;
    }
    public String addTermin(){
        if(!added){
            Termin aktuellerTermin = kalenderCDI.getKalender().getAktuellerTermin();
            for(Termin termin:kalenderCDI.getKalender().getGesetzteTermine()){
                if(termin.getZimmernummer()==aktuellerTermin.getZimmernummer()){
                    //Wenn die Zeiten mit anderen Zeiten überlappen
                    if(aktuellerTermin.getVon().before(termin.getBis()) && aktuellerTermin.getBis().after(termin.getVon())){
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
            if(aktuellerTermin.getVon().after(aktuellerTermin.getBis())){
                FacesMessage message = new FacesMessage("'Von' kann nicht nach 'Bis' sein");
                FacesContext context = FacesContext.getCurrentInstance();
                context.addMessage("", message);
                return null;
            }
            kalenderCDI.getKalender().addTermin();

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
