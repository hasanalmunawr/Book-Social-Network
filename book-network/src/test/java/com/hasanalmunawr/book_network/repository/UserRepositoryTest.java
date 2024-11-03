package com.hasanalmunawr.book_network.repository;

import com.hasanalmunawr.book_network.auth.model.entity.UserEntity;
import com.hasanalmunawr.book_network.auth.model.enums.Role;
import com.hasanalmunawr.book_network.auth.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @TestConfiguration
    static class TestConfig {
        @Bean
        public PasswordEncoder passwordEncoder() {
            return new BCryptPasswordEncoder();
        }

        @Bean
        public AuditorAware<String> auditorAware() {
            return () -> Optional.of("test-auditor");
        }
    }

    @Test
    void saveUserTest() {
        // Arrange
        UserEntity user = UserEntity.builder()
                .firstname("Hasan")
                .lastname("Almunawar")
                .email("hasanalmunawr@example.com")
                .password(passwordEncoder.encode("password123"))
                .role(Role.ADMIN)
                .build();

        // Action
        UserEntity save = userRepository.save(user);

        // Assert
        assertNotNull(save);
    }

    @Test
    void getByUserIdTest() {
        UserEntity user = UserEntity.builder()
                .firstname("Hasan")
                .lastname("Almunawar")
                .email("hasanalmunawr@example.com")
                .password(passwordEncoder.encode("password123"))
                .role(Role.ADMIN)
                .build();

        UserEntity save = userRepository.save(user);
        UserEntity resultGetUserId = userRepository.getReferenceById(save.getId());

        assertNotNull(resultGetUserId);
        assertEquals(save.getId(), resultGetUserId.getId());
    }

    @Test
    void getByUserEmailTest() {
        UserEntity user = UserEntity.builder()
                .firstname("Hasan")
                .lastname("Almunawar")
                .email("hasanalmunawr@example.com")
                .password(passwordEncoder.encode("password123"))
                .role(Role.ADMIN)
                .build();

        UserEntity save = userRepository.save(user);
        Optional<UserEntity> resultGetUserId = userRepository.findByEmail(save.getEmail());

        assertNotNull(resultGetUserId);
        assertEquals(save.getEmail(), resultGetUserId.get().getEmail());
    }


}
