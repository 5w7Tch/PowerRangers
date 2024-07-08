package models.USER;

import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class EmailSender {

    private static volatile EmailSender instance;
    private static final String EMAIL_HOST = "QuizTime4444@gmail.com";
    private static final String EMAIL_HOST_PASSWORD = "A1b!cD2$eFg3H4iJ";
    private EmailSender() {

    }

    // Public method to provide access to the instance
    public static EmailSender getInstance() {
        if (instance == null) {
            instance = new EmailSender();
        }
        return instance;
    }


    public void sendEmail(String receiver, String subject, String body) {

        String from = "no-reply@example.com";
        String host = "smtp.gmail.com";

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", "587");
        
        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(EMAIL_HOST, EMAIL_HOST_PASSWORD);
                    }
                });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(receiver));
            message.setSubject(subject);
            message.setText(body);

            Transport.send(message);

        } catch (MessagingException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
