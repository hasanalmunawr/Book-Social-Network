package com.hasanalmunawr.book_network.auth.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class RegistrationResponse {

    private String message;
    private int statusCode;
    private String activateUrl;
    private UserDetailsDto userDetails;
}
