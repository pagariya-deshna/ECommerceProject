package com.ecommerce.project.serviceImpl;


import java.util.Properties;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EmailSender {

    private static final Logger log = LoggerFactory.getLogger(EmailSender.class);

//    public static void main(String[] args) {
//        // Replace these constants with your email and password
//        final String EMAIL_ADDRESS = "deshnapagariya1010@outlook.com";
//        final String PASSWORD = "1234@890";
//
//        // Replace with the recipient email, subject, and message text
//        String recipientEmail = "deshnapagariya.ngd@gmail.com";
//        String subject = "My first smtp configuration";
//        String messageText = "<h1>This is a test email</h1>";
//
//        boolean result = sendEmail(EMAIL_ADDRESS, PASSWORD, recipientEmail, subject, messageText);
//        if (result) {
//            log.info("Email sent successfully!!");
//        } else {
//            log.error("Failed to send email.");
//        }
//    }

    public static boolean sendEmail(String emailAddress, String password, String recipientEmail, String subject, String messageText) {
        try {
            Properties props = new Properties();
            props.put("mail.smtp.host", "smtp-mail.outlook.com");
            props.put("mail.smtp.port", "587");
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.auth", "true");
            props.put("mail.debug", "true");
            
            // Explicitly specify the protocol
            props.put("mail.smtp.ssl.protocols", "TLSv1.2");

            Session session = Session.getInstance(props, new javax.mail.Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(emailAddress, password);
                }
            });

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(emailAddress));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail));
            message.setSubject(subject);
            message.setContent(messageText, "text/html;charset=utf-8");
            message.setHeader("Content-Type", "text/html;charset=utf-8");

            Transport.send(message);
            return true;
        } catch (Exception e) {
            log.error("Error: " + e.getMessage(), e);
            return false;
        }
    }
}




