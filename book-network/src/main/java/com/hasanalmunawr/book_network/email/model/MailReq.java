package com.hasanalmunawr.book_network.email.model;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class MailReq {
    private String to;
    private String username;
    private EmailTemplateName emailTemplate;
    private String confirmationUrl;
    private String activationCode;
    private String subject;
}
