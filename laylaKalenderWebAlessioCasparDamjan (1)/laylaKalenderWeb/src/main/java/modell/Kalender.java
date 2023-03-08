package modell;

import com.google.gson.Gson;
import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMultipart;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class Kalender {
    private List<Termin> gesetzteTermine = new ArrayList<Termin>();
    private Termin aktuellerTermin = new Termin();

    public Kalender(){

    }

    public void addTermin(){
        aktuellerTermin.setPublicKey(generateRandomKey(10));
        aktuellerTermin.setPrivateKey(generateRandomKey(10));
        gesetzteTermine.add(aktuellerTermin);

        createJson();

        try {
            send();
        } catch (Exception ex) {
        }

        aktuellerTermin = new Termin();
    }
    public void editTermin(){
        for(int i = 0;i<gesetzteTermine.size();i++){
            if(gesetzteTermine.get(i).getPrivateKey().equals(aktuellerTermin.getPrivateKey())){
                gesetzteTermine.set(i,aktuellerTermin);

                createJson();

                try {
                    send();
                } catch (Exception ex){
                }
                break;
            }
        }

        aktuellerTermin = new Termin();
    }
    public void removeTermin(){
        for(int i = 0;i<gesetzteTermine.size();i++){
            if(gesetzteTermine.get(i).getPrivateKey().equals(aktuellerTermin.getPrivateKey())){
                gesetzteTermine.remove(i);
                createJson();
                break;
            }
        }

        aktuellerTermin = new Termin();
    }
    public void createJson(){
        try {
            Gson gson = new Gson();
            String json=gson.toJson(gesetzteTermine);

            File file = new File("termins.json");
            try (PrintStream out = new PrintStream(new FileOutputStream(file))) {
                out.print(json);
            }
        } catch (Exception ex){

        }
    }
    public String generateRandomKey(int n)
    {
        String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                + "0123456789"
                + "abcdefghijklmnopqrstuvxyz";

        StringBuilder sb = new StringBuilder(n);
        for (int i = 0; i < n; i++) {
            int index
                    = (int)(AlphaNumericString.length()
                    * Math.random());

            sb.append(AlphaNumericString
                    .charAt(index));
        }

        return sb.toString();
    }

    public List<Termin> getGesetzteTermine() {
        return gesetzteTermine;
    }

    public void setGesetzteTermine(List<Termin> gesetzteTermine) {
        this.gesetzteTermine = gesetzteTermine;
    }

    public Termin getAktuellerTermin() {
        return aktuellerTermin;
    }

    public void setAktuellerTermin(Termin aktuellerTermin) {
        this.aktuellerTermin = aktuellerTermin;
    }


    public void send() throws MessagingException, IOException {
        //Login Information
        String username = "prof.shitpost420@gmail.com";
        String password = "knxyrkzjjanaygoi";

        String Ownerreceiver = aktuellerTermin.getGruender();

        //Creating the Props
        Properties prop = new Properties();
        prop.put("mail.smtp.auth", true);
        prop.put("mail.smtp.starttls.enable", "true");
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", "587");

        //Create Session
        Session session = Session.getInstance(prop, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        Message messageowner = new MimeMessage(session);
        messageowner.setFrom(new InternetAddress("prof.shitpost420@gmail.com", "Layla Kalender - Admin Mail"));

        Message messageparticipant = new MimeMessage(session);
        messageparticipant.setFrom(new InternetAddress("prof.shitpost420@gmail.com", "Layla Kalender - Participant Mail"));

        //Email to Owner
        messageowner.setRecipients(Message.RecipientType.TO, InternetAddress.parse(Ownerreceiver));

        //Email Subject
        messageowner.setSubject("Layla Kalender - Key Distribution");
        messageparticipant.setSubject("Layla Kalender - Key Distribution");

        //Divide E-Mail Into Parts
        Multipart multipart = new MimeMultipart();
        MimeBodyPart mimeBodyPart = new MimeBodyPart();

        Multipart multipartparticipant = new MimeMultipart();
        MimeBodyPart mimeBodyPartparticipant = new MimeBodyPart();

        //Set Email Content for Owner
        String messageOwner = "<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "<body>\n" +
                "    <div style=\"text-align: center; border-color: grey; border-style: solid; width: auto; margin-left: 20%; margin-right: 20%; box-shadow: 10px 5px 5px grey; border-left: 20%; border-right: 20%; background-image: url(https://i.pinimg.com/originals/20/6a/eb/206aeb50a2f790d25c1ff54b6011b0a7.jpg)\">\n" +
                "        <div id=\"header\">\n" +
                "            <a href=\"index.xhtml\">\n" +
                "                <img src=\"https://cdn.discordapp.com/attachments/985795697783762965/985879958729662494/Unbenannt.png\" id=\"imgHeader\" style=\"height: 60px;\" class=\"logo\" alt=\"Layla Logo\"/>\n" +
                "            </a>\n" +
                "        </div>\n" +
                "        <p style=\"font-weight: bold; color: white;\">Thank you for Renting a Room with Layla Kalender for your meeting!</p>\n" +
                "        <p style=\"color: white;\">Private Key:  "+aktuellerTermin.getPrivateKey()+"</p>\n" +
                "        <p style=\"color: white;\">Public Key:  "+aktuellerTermin.getPublicKey()+"</p>\n" +
                "        <p style=\"color: darkgrey; font-style: italic;\">Please bear in mind that if you decide to cancel the meeting, do it atleast 24 hours beforehand.</p>\n" +
                "        <div style=\"background-image: url(https://dmkz2i5qfmsty.cloudfront.net/76778380-9ff1-47f9-833c-dffb0f5e0928.jpg); border-color: grey; border-style: solid; padding: 20%; background-size: cover; \"  >     \n" +
                "        </div>\n" +
                "        </body>\n" +
                "    </div>\n" +
                "</body>\n" +
                "</html>";

        //Set Email Content for normal Participants
        String messageParticipant = "<html lang=\"en\">\n" +
                "<body>\n" +
                "    <div style=\"text-align: center; border-color: grey; border-style: solid; width: auto; margin-left: 20%; margin-right: 20%; box-shadow: 10px 5px 5px grey; border-left: 20%; border-right: 20%; background-image: url(https://i.pinimg.com/originals/20/6a/eb/206aeb50a2f790d25c1ff54b6011b0a7.jpg)\">\n" +
                "        <div id=\"header\">\n" +
                "            <a href=\"index.xhtml\">\n" +
                "                <img src=\"https://cdn.discordapp.com/attachments/985795697783762965/985879958729662494/Unbenannt.png\" id=\"imgHeader\" style=\"height: 60px;\" class=\"logo\" alt=\"Layla Logo\"/>\n" +
                "            </a>\n" +
                "        </div>\n" +
                "        <p style=\"font-weight: bold; color: white;\">Thank you for Renting a Room with Layla Kalender for your meeting!</p>\n" +
                "        <p style=\"color: white;\">Public Key:  "+aktuellerTermin.getPublicKey()+"</p>\n" +
                "        <p style=\"color: white;\">Enter the public Key on our Webiste to view all Information regaring you Meeting!</p>\n" +
                "        <p style=\"color: darkgrey; font-style: italic;\">Please bear in mind that if you decide to cancel the meeting, do it atleast 24 hours beforehand.</p>\n" +
                "        <div style=\"background-image: url(https://dmkz2i5qfmsty.cloudfront.net/76778380-9ff1-47f9-833c-dffb0f5e0928.jpg); border-color: grey; border-style: solid; padding: 20%; background-size: cover; \"  >     \n" +
                "        </div>\n" +
                "        </body>\n" +
                "    </div>\n" +
                "</body>\n" +
                "</html>";

        //Owner Mail Send
        mimeBodyPart.setContent(messageOwner, "text/html; charset=utf-8");
        multipart.addBodyPart(mimeBodyPart);
        messageowner.setContent(multipart);
        Transport.send(messageowner);

        //Other Participant Mail Send
        for ( String participantemail : aktuellerTermin.getTeilnehmer()) {
            messageparticipant.setRecipients(Message.RecipientType.TO, InternetAddress.parse(participantemail));
            mimeBodyPartparticipant.setContent(messageParticipant, "text/html; charset=utf-8");
            multipartparticipant.addBodyPart(mimeBodyPartparticipant);
            messageparticipant.setContent(multipartparticipant);
            Transport.send(messageparticipant);
        }
    }
}
