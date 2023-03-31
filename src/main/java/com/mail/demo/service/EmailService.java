package com.mail.demo.service;


import com.mail.demo.dto.MailFormDTO;
import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.search.FlagTerm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Properties;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    Logger logger = LoggerFactory.getLogger(EmailService.class);

    public void readEmails() throws Exception {

        Properties properties = new Properties();
        properties.setProperty("mail.imap.ssl.enable", "true");
        Session session = Session.getInstance(properties);
        Store store = session.getStore("imap");
        store.connect("imap.gmail.com", "vaniagatman@gmail.com", "sffakyrofiixmbut");
        Folder inbox = store.getFolder("INBOX");
        inbox.open(Folder.READ_ONLY);
        Flags flags = new Flags(Flags.Flag.SEEN);
        FlagTerm flagTerm = new FlagTerm(flags, true);
        Message[] messages = inbox.search(flagTerm);


        int count = 0;
        for (Message message : messages) {
            System.out.println("From: " + InternetAddress.toString(message.getFrom()));
            System.out.println("Subject: " + message.getSubject());
            System.out.println("Sent Date: " + message.getSentDate());
            System.out.println("Message: " + message.getContent().toString());

            count++;
            if (count == 5) {
                break;
            }
        }

        // Close the folder and store objects
        inbox.close(false);
        store.close();
    }

    public void sendEmail(MailFormDTO destination) {
        logger.debug(destination.sentTo());
        logger.debug(destination.subject());
        logger.debug(destination.text());

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("vaniagatman@gmail.com");
        message.setTo(destination.sentTo());
        message.setSubject(destination.subject());
        message.setText(destination.text());
        javaMailSender.send(message);

        logger.debug("Mail message sent: " + message);
    }

}


