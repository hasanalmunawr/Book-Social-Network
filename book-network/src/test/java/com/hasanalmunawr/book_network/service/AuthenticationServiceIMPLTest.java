package com.hasanalmunawr.book_network.service;

import com.hasanalmunawr.book_network.auth.model.dto.AuthenticationRequest;
import com.hasanalmunawr.book_network.auth.model.dto.AuthenticationResponse;
import com.hasanalmunawr.book_network.auth.model.dto.RegistrationRequest;
import com.hasanalmunawr.book_network.auth.model.entity.UserEntity;
import com.hasanalmunawr.book_network.auth.model.enums.Role;
import com.hasanalmunawr.book_network.auth.repository.UserRepository;
import com.hasanalmunawr.book_network.auth.service.AuthenticationService;
import com.hasanalmunawr.book_network.auth.service.impl.AuthenticationServiceImpl;
import jakarta.mail.MessagingException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthenticationServiceIMPLTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private AuthenticationServiceImpl authenticationService;

  /*  @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }*/

    @Test
    void registerSuccessTest() throws MessagingException {
        RegistrationRequest register = new RegistrationRequest(
                "Hasan",
                "hasan@example.com",
                "password123,",
                "admin"
        );

        when(userRepository.findByEmail(register.email())).thenReturn(Optional.empty());
        UserEntity userMock = userRepository.save(
                UserEntity.builder()
                .firstname(register.username())
                .email(register.email())
                .password(passwordEncoder.encode(register.password()))
                .role(Role.ADMIN).build()
        );
        when(userRepository.save(any(UserEntity.class))).thenReturn(userMock);

        authenticationService.register(register);

    }

    @Test
    void loginSuccessTest() throws MessagingException {
        // Arrange
        UserEntity user = UserEntity.builder()
                .firstname("Hasan")
                .lastname("Almunawar")
                .email("hasanalmunawr@example.com")
                .password(passwordEncoder.encode("password123")) // Use the encoded password here
                .role(Role.ADMIN)
                .build();

        AuthenticationRequest request = new AuthenticationRequest(
                user.getEmail(),
                "password123"
        );

        // Mock the UserRepository, PasswordEncoder, and Authentication
        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("password123", user.getPassword())).thenReturn(true);

        Authentication mockAuthentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(mockAuthentication);

        // Act
        AuthenticationResponse response = authenticationService.login(request);

        // Assert
        assertNotNull(response);
        verify(userRepository, times(1)).findByEmail(user.getEmail());
        verify(passwordEncoder, times(1)).matches("password123", user.getPassword());
    }
}
