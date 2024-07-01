package com.hasanalmunawr.book_network.service;

import com.hasanalmunawr.book_network.auth.AuthenticationRequest;
import com.hasanalmunawr.book_network.auth.AuthenticationResponse;
import com.hasanalmunawr.book_network.auth.RegistrationRequest;
import jakarta.mail.MessagingException;

public interface AuthenticationService {

    void register(RegistrationRequest registrationRequest) throws MessagingException;
    void activateAccount(String token) throws MessagingException;
    AuthenticationResponse login(AuthenticationRequest authenticationRequest) throws MessagingException;
}
