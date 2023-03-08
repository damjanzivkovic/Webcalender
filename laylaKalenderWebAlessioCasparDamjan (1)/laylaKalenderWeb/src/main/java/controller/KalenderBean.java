package controller;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMultipart;
import modell.Kalender;
import modell.Termin;

import javax.enterprise.context.ApplicationScoped;
import javax.faces.bean.ViewScoped;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Properties;

@ApplicationScoped
public class KalenderBean {
    private Kalender kalender;

    public KalenderBean(){
        kalender = new Kalender();

        try {
            String file = "termins.json";
            String json = new String(Files.readAllBytes(Paths.get(file)));
            Gson gson = new Gson();
            Type type = new TypeToken<Collection<Termin>>() {}.getType();
            kalender.setGesetzteTermine(gson.fromJson(json, type));
        }catch(Exception ex){

        }
    }
    public Kalender getKalender() {
        return kalender;
    }

    public void setKalender(Kalender kalender) {
        this.kalender = kalender;
    }
}
