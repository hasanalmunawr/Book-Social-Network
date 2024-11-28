package com.hasanalmunawr.book_network.mapper;

import com.hasanalmunawr.book_network.book.model.dto.BookRequest;
import com.hasanalmunawr.book_network.book.model.dto.BookResponse;
import com.hasanalmunawr.book_network.book.model.entity.BookEntity;
import com.hasanalmunawr.book_network.book.model.mapper.BookMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.awt.print.Book;

@ExtendWith(MockitoExtension.class)
class BookMapperTest {

    @InjectMocks
    private BookMapper bookMapper;


    private BookRequest bookRequest;
    private BookEntity bookEntity;
    private BookResponse bookResponse;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);



        BookEntity book = BookEntity.builder()
                .title("Mockito Title")
                .archived(false)
                .authorName("Mockito")
                .synopsis("Mockito Synopsis")
                .isbn("124124124")
                .bookCover("pasfajfr")
//                .synopsis()
                .build();
    }
}
