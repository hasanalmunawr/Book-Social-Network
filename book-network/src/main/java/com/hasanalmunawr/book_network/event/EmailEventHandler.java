package com.hasanalmunawr.book_network.event;

import com.hasanalmunawr.book_network.model.MailReq;
import com.hasanalmunawr.book_network.service.EmailService;
import jakarta.validation.constraints.Email;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

@RequiredArgsConstructor
public class EmailEventHandler {

    private final EmailService emailService;

    @SneakyThrows
    public void onEmailSent(MailReq email) {
        emailService.sendEmail(
                email.getTo(),
                email.getUsername(),
                email.getEmailTemplate(),
                email.getConfirmationUrl(),
                email.getActivationCode(),
                email.getSubject()
        );
    }
}
