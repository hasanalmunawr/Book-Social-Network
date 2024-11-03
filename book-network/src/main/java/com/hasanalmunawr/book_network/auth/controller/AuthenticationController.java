package com.hasanalmunawr.book_network.auth.controller;


import com.hasanalmunawr.book_network.auth.model.dto.*;
import com.hasanalmunawr.book_network.auth.service.AuthenticationService;
import com.hasanalmunawr.book_network.common.response.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication")
@Slf4j
class AuthenticationController {


    private final AuthenticationService service;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<ApiResponse<RegistrationResponse>> register(
            @RequestBody @Valid RegistrationRequest request
    ) throws MessagingException {
        var registerResponse = service.register(request);

        ApiResponse<RegistrationResponse> response = ApiResponse.<RegistrationResponse>builder()
                .success(true)
                .message("Successfully registered")
                .data(registerResponse)
                .build();

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(response);
    }

    @PostMapping("/activate-account")
    public ResponseEntity<ActivateResponse> confirm(
            @RequestParam String token
    ) throws MessagingException {
        var activateResponse = service.activateAccount(token);
        return ResponseEntity.ok(activateResponse);
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody AuthenticationRequest request
    ) throws MessagingException {
        var loginResponse = service.login(request);
        return ResponseEntity.ok(loginResponse);
    }




}
