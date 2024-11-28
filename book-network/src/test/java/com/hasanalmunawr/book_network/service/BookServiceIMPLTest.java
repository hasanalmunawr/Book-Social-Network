package com.hasanalmunawr.book_network.service;

import com.hasanalmunawr.book_network.book.model.dto.BookRequest;
import com.hasanalmunawr.book_network.book.repository.BookRepository;
import com.hasanalmunawr.book_network.book.service.BookService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.awt.print.Book;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BookServiceIMPLTest {


    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookService bookService;

    @Test
    void bookService_saveBook_ReturnBookDto() {
        BookRequest request = new BookRequest(
                "Ayo Membaca Buku",
                "Hasan Almu",
                "19419700",
                "NOthing Happend",
                true
        );



//        when(bookRepository.save(request)).thenReturn(new Book());
    }
}
