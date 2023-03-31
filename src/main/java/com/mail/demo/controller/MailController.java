package com.mail.demo.controller;

import com.mail.demo.dto.MailFormDTO;
import com.mail.demo.service.EmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.Multipart;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

@RestController
@RequestMapping("/api")
public class MailController {

    Logger logger = LoggerFactory.getLogger(EmailService.class);

    private final EmailService emailService;

    public MailController(EmailService emailService) {
        this.emailService = emailService;
    }

    @GetMapping("/read-emails")
    public void readEmails() throws Exception {
        emailService.readEmails();
    }

    @PostMapping("/send-email")
    public void sendEmail(@RequestBody MailFormDTO mailForm) {
        logger.info(mailForm.sentTo());
        logger.info(mailForm.subject());
        logger.info(mailForm.text());
        try {
            emailService.sendEmail(mailForm);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}
