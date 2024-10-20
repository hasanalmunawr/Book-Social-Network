package com.hasanalmunawr.book_network.auth.service.impl;

import com.hasanalmunawr.book_network.auth.model.dto.AuthenticationRequest;
import com.hasanalmunawr.book_network.auth.model.dto.AuthenticationResponse;
import com.hasanalmunawr.book_network.auth.model.dto.RegistrationRequest;
import com.hasanalmunawr.book_network.email.model.EmailTemplateName;
import com.hasanalmunawr.book_network.exception.custom.UserAlreadyExistException;
import com.hasanalmunawr.book_network.auth.model.enums.Role;
import com.hasanalmunawr.book_network.security.JwtService;
import com.hasanalmunawr.book_network.auth.service.AuthenticationService;
import com.hasanalmunawr.book_network.email.service.EmailService;
import com.hasanalmunawr.book_network.auth.model.entity.TokenEntity;
import com.hasanalmunawr.book_network.auth.repository.TokenRepository;
import com.hasanalmunawr.book_network.auth.model.entity.UserEntity;
import com.hasanalmunawr.book_network.auth.repository.UserRepository;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Optional;

//@Service
@RequiredArgsConstructor
@Slf4j
class AuthenticationServiceImpl implements AuthenticationService {


    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;
    private final JwtService jwtService;
    private final TokenRepository tokenRepository;
    private final AuthenticationManager authenticationManager;

    @Value("${application.mailing.frontend.activation-url}")
    private String activationUrl;

    @Value("${application.security.jwt.expiration}")
    private long expiryToken;

    @Override
    public void register(RegistrationRequest request) throws MessagingException {
        Optional<UserEntity> byEmail = userRepository.findByEmail(request.email());
        if (byEmail.isPresent()) {
            log.warn("User with email {} already exists", request.email());
            throw new UserAlreadyExistException();
        }

        UserEntity user = UserEntity.builder()
                .firstname(request.firstName())
                .lastname(request.lastName())
                .email(request.email())
//                .password(passwordEncoder.encode(request.password()))
                .accountLocked(false)
                .enabled(false)
                .role(convertToRole(request.role()))
                .build();

        userRepository.save(user);
        sendValidationEmail(user, EmailTemplateName.ACTIVATE_ACCOUNT);
    }

    @Override
    public void activateAccount(String token) throws MessagingException {
        TokenEntity savedToken = tokenRepository.findByToken(token)
                // todo exception has to be defined
                .orElseThrow(() -> new RuntimeException("Invalid token"));
        if (LocalDateTime.now().isAfter(savedToken.getExpiresAt())) {
            sendValidationEmail(savedToken.getUser(), EmailTemplateName.ACTIVATE_ACCOUNT);
            throw new RuntimeException("Activation token has expired. A new token has been send to the same email address");
        }

        var user = userRepository.findById(savedToken.getUser().getId())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        user.setEnabled(true);
        userRepository.save(user);

        savedToken.setValidatedAt(LocalDateTime.now());
        tokenRepository.save(savedToken);
    }

    @Override
    public AuthenticationResponse login(AuthenticationRequest request) throws MessagingException {
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.email(),
                        request.password()
                )
        );

        var claims = new HashMap<String, Object>();
        var user = ((UserEntity) auth.getPrincipal());
        user.setLastLogin(LocalDateTime.now());
        claims.put("fullName", user.getFullName());
        userRepository.save(user);

        var jwtToken = jwtService.generateToken(claims, (UserEntity) auth.getPrincipal());
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();

    }


    private Role convertToRole(String role) {
        // JUST RECEIVE ROLE EITHER 'admin' OR 'user'
        if (role.equalsIgnoreCase("admin")) {
            return Role.ADMIN;
        } else if (role.equalsIgnoreCase("user")) {
            return Role.USER;
        } else {
            throw new IllegalArgumentException("Invalid role: " + role);
        }
    }

    private void sendValidationEmail(UserEntity user, EmailTemplateName templateName) throws MessagingException, MessagingException {
        var newToken = generateAndSaveActivationToken(user);

        emailService.sendEmailGoogle(
                user.getEmail(),
                user.getFullName(),
                templateName,
                newToken,
                "Account activation"
        );
    }

    private String generateAndSaveActivationToken(UserEntity user) {
        // Generate a token
        String generatedToken = generateActivationCode(6);
        var token = TokenEntity.builder()
                .token(generatedToken)
                .createdAt(LocalDateTime.now())
                .expiresAt(LocalDateTime.now().plusMinutes(15))
                .user(user)
                .build();
        tokenRepository.save(token);

        return generatedToken;
    }

    private String generateActivationCode(int length) {
        String characters = "0123456789";
        StringBuilder codeBuilder = new StringBuilder();

        SecureRandom secureRandom = new SecureRandom();

        for (int i = 0; i < length; i++) {
            int randomIndex = secureRandom.nextInt(characters.length());
            codeBuilder.append(characters.charAt(randomIndex));
        }

        return codeBuilder.toString();
    }

}
