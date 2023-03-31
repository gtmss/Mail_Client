package com.mail.demo.dto;

public record MailFormDTO(
        String sentTo,
        String subject,
        String text
) {
}
