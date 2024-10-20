package com.hasanalmunawr.book_network.event;

import com.hasanalmunawr.book_network.email.model.MailReq;
import com.hasanalmunawr.book_network.email.service.EmailService;
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
