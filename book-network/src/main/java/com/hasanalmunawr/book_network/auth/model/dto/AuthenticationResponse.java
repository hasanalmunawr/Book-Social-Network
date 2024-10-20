package com.hasanalmunawr.book_network.auth.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class AuthenticationResponse {
    private String token;

    @JsonProperty("access_token_expiry")
    private String tokenExpiry;
}
