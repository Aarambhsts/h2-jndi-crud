package com.unisys.service;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.internet.MimeMessage;
import jakarta.mail.MessagingException;

/**
 * Service class that handles the sending of emails.
 */
@Service
public class EmailService {

    private final JavaMailSender javaMailSender;

    public EmailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    /**
     * Sends a simple text email.
     * 
     * @param to      the recipient's email address.
     * @param subject the subject of the email.
     * @param text    the body text of the email.
     * @throws EmailServiceException if sending the email fails.
     */
    public void sendSimpleEmail(String to, String subject, String text) {
        MimeMessage message = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, false, "UTF-8");
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(text);
            javaMailSender.send(message);
        } catch (MessagingException e) {
            throw new EmailServiceException("Failed to send email: " + e.getMessage(), e);
        }
    }
}

/**
 * Custom exception for email-related errors.
 */
class EmailServiceException extends RuntimeException {
    public EmailServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
