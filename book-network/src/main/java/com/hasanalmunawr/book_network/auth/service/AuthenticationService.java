package com.hasanalmunawr.book_network.auth.service;

import com.hasanalmunawr.book_network.auth.model.dto.*;
import jakarta.mail.MessagingException;

public interface AuthenticationService {

    RegistrationResponse register(RegistrationRequest registrationRequest) throws MessagingException;
    ActivateResponse activateAccount(String token) throws MessagingException;
    AuthenticationResponse login(AuthenticationRequest authenticationRequest) throws MessagingException;
}
