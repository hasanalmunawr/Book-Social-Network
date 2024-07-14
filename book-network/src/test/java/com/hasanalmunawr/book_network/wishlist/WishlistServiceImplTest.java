package com.hasanalmunawr.book_network.wishlist;

import com.hasanalmunawr.book_network.book.BookEntity;
import com.hasanalmunawr.book_network.book.BookMapper;
import com.hasanalmunawr.book_network.book.BookRepository;
import com.hasanalmunawr.book_network.book.BookResponse;
import com.hasanalmunawr.book_network.common.PageResponse;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class WishlistServiceImplTest {


    @Mock
    private WishlistRepository wishlistRepository;

    @Mock
    private BookRepository bookRepository;

    @Mock
    private BookMapper bookMapper;

    @InjectMocks
    private WishlistServiceImpl wishlistService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAddBookToWishlist() {
        // Arrange
        Integer bookId = 1;
        String username = "user";
        Authentication authentication = mock(Authentication.class);
        BookEntity bookEntity = new BookEntity();
        bookEntity.setId(bookId);

        when(authentication.getName()).thenReturn(username);
        when(bookRepository.findById(bookId)).thenReturn(Optional.of(bookEntity));

        // Act
        wishlistService.add(authentication, bookId);

        // Assert
        verify(bookRepository).findById(bookId);
        verify(wishlistRepository).save(any(WishlistEntity.class));
    }

    @Test
    public void testAddBookToWishlist_BookNotFound() {
        // Arrange
        Integer bookId = 1;
        Authentication authentication = mock(Authentication.class);

        when(bookRepository.findById(bookId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(EntityNotFoundException.class, () -> wishlistService.add(authentication, bookId));
    }

    @Test
    public void testGetAllWishlistByUser() {
        // Arrange
        int page = 0;
        int size = 10;
        String username = "user";
        Authentication authentication = mock(Authentication.class);
        BookEntity bookEntity = new BookEntity();
        bookEntity.setId(1);
        Page<BookEntity> bookPage = new PageImpl<>(List.of(bookEntity));
        BookResponse bookResponse = new BookResponse();

        when(authentication.getName()).thenReturn(username);
        when(wishlistRepository.findAllWishlistByUser(any(Pageable.class), eq(username))).thenReturn(bookPage);
        when(bookMapper.toBookResponse(any(BookEntity.class))).thenReturn(bookResponse);

        // Act
        PageResponse<BookResponse> result = wishlistService.getAllWishlistByUser(authentication, size, page);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        assertEquals(1, result.getTotalPages());
        assertEquals(bookResponse, result.getContent().get(0));

        verify(wishlistRepository).findAllWishlistByUser(any(Pageable.class), eq(username));
        verify(bookMapper).toBookResponse(any(BookEntity.class));
    }

}