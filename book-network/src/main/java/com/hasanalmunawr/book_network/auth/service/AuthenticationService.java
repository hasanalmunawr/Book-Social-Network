package com.hasanalmunawr.book_network.auth.service;

import com.hasanalmunawr.book_network.auth.model.dto.AuthenticationRequest;
import com.hasanalmunawr.book_network.auth.model.dto.AuthenticationResponse;
import com.hasanalmunawr.book_network.auth.model.dto.RegistrationRequest;
import jakarta.mail.MessagingException;

public interface AuthenticationService {

    void register(RegistrationRequest registrationRequest) throws MessagingException;
    void activateAccount(String token) throws MessagingException;
    AuthenticationResponse login(AuthenticationRequest authenticationRequest) throws MessagingException;
}
