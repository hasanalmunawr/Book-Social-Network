package com.hasanalmunawr.book_network.book;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hasanalmunawr.book_network.security.JwtFilter;
import com.hasanalmunawr.book_network.security. JwtService;
import com.hasanalmunawr.book_network.service.BookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.core.Authentication;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BookController.class)
@ExtendWith(MockitoExtension.class)
@Import({JwtService.class, JwtFilter.class})
class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookService service;

    @InjectMocks
    private BookController controller;

    @Mock
    private Authentication authentication;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp(WebApplicationContext webApplicationContext) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    void saveBook() throws Exception {
        // Prepare mock data
        BookRequest request = new BookRequest(
                "Food Free",
                "GEZZ",
                "892201",
                "Nothing",
                true

        );
        // populate request with necessary data

        when(service.save(any(BookRequest.class), any(Authentication.class))).thenReturn(1);

        // Perform POST request
        mockMvc.perform(post("/books")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(request))
                        .principal(authentication))
                .andExpect(status().isOk());
    }

    @Test
    void findBookById() {
    }

    @Test
    void findAllBooks() {
    }

    @Test
    void findAllBooksByOwner() {
    }
}