package com.hasanalmunawr.book_network.repository;

import com.hasanalmunawr.book_network.auth.model.entity.UserEntity;
import com.hasanalmunawr.book_network.auth.model.enums.Role;
import com.hasanalmunawr.book_network.auth.repository.UserRepository;
import com.hasanalmunawr.book_network.book.model.entity.BookEntity;
import com.hasanalmunawr.book_network.book.repository.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class BookRepositoryTest {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private UserRepository userRepository;

    @MockBean
    private PasswordEncoder passwordEncoder;

    @TestConfiguration
    static class TestConfig {
        @Bean
        public AuditorAware<String> auditorAware() {
            return () -> Optional.of("test-auditor");
        }
    }

    private UserEntity savedUser;

    @BeforeEach
    void setUp() {
        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");

        UserEntity user = UserEntity.builder()
                .firstname("Hasan")
                .lastname("Almunawar")
                .email("hasanalmunawr@example.com")
                .password(passwordEncoder.encode("password123"))
                .role(Role.ADMIN)
                .build();

        savedUser = userRepository.save(user);
    }

    @Test
    void saveBookTest() {
        BookEntity book = BookEntity.builder()
                .title("Unit Test")
                .authorName("Unit Test")
                .isbn("123123123")
                .synopsis("Unit Test")
                .owner(savedUser)
                .build();

        BookEntity savedBook = bookRepository.save(book);

        assertNotNull(savedBook);
        assertNotNull(savedBook.getId());
    }

    @Test
    void findBookByIdTest() {
        BookEntity book = BookEntity.builder()
                .title("Unit Test")
                .authorName("Unit Test")
                .isbn("123123123")
                .synopsis("Unit Test")
                .owner(savedUser)
                .build();

        BookEntity savedBook = bookRepository.save(book);
        Optional<BookEntity> byId = bookRepository.findById(savedBook.getId());

        assertNotNull(savedBook);
        assertEquals(savedBook.getId(), byId.get().getId());
    }
}
