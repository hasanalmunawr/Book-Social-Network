package com.hasanalmunawr.book_network.email.service;

import com.hasanalmunawr.book_network.email.model.EmailTemplateName;
import jakarta.mail.MessagingException;

public interface EmailService {

    void sendEmail(
            String to,
            String username,
            EmailTemplateName emailTemplate,
            String confirmationUrl,
            String activationCode,
            String subject
    ) throws MessagingException;

    void sendEmailGoogle(
            String to,
            String username,
            EmailTemplateName emailTemplate,
            String activationCode,
            String subject
    ) throws MessagingException;
}
