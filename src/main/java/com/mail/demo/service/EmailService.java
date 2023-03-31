package com.mail.demo.service;


import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.search.FlagTerm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Properties;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

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

    }


