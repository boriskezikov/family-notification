package ru.family.notification.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import ru.family.notification.config.MailConstants;
import ru.family.notification.config.MailPropsProvider;
import ru.family.notification.dto.CredentialsDTO;
import ru.family.notification.dto.TwoFaDTO;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import static ru.family.notification.config.MailConstants.PASSWORD;
import static ru.family.notification.config.MailConstants.USERNAME;


@Service
@Slf4j
public class EmailService extends Authenticator {

    private final MailPropsProvider propsProvider;

    @Autowired
    public EmailService(MailPropsProvider propsProvider) {
        this.propsProvider = propsProvider;
    }

    @Async
    public void send2Fa(TwoFaDTO user) {

        try {
            MimeMessage message = new MimeMessage(getSession());
            message.setFrom(new InternetAddress(USERNAME));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(user.getMail()));
            message.setSubject("Green App auth");

            message.setText(String.format(
                    "Your verification code: %s \nRegards,\nGreenApp team."
                    , user.getTwoFaCode()));

            Transport.send(message);
        } catch (MessagingException e) {
            log.error(e.getMessage());
        }
    }

    public void resetPassword(CredentialsDTO creds) {

        try {
            MimeMessage message = new MimeMessage(getSession());
            message.setFrom(new InternetAddress(USERNAME));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(creds.getUsername()));
            message.setSubject("GreenApp Password changed");

            message.setText(String.format(
                    "Hi! Your password has been updated!" +
                            "\nYour credentials now: " +
                            "\n\tUsername: %s" +
                            "\n\tPassword: %s  " +
                            "\nRegards,\nFamily-app team."
                    , creds.getUsername(), creds.getPassword()));

            Transport.send(message);
        } catch (MessagingException e) {
            log.error(e.getMessage());
        }
    }



    private Session getSession() {
        return Session.getInstance(propsProvider.getMailProps(), new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(USERNAME, PASSWORD);
            }
        });
    }
}
