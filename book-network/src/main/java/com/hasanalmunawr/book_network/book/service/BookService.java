package com.hasanalmunawr.book_network.book.service;

import com.hasanalmunawr.book_network.book.model.dto.BookRequest;
import com.hasanalmunawr.book_network.book.model.dto.BookResponse;
import com.hasanalmunawr.book_network.book.model.dto.BorrowedBookResponse;
import com.hasanalmunawr.book_network.book.model.dto.PageResponse;
import org.springframework.security.core.Authentication;
import org.springframework.web.multipart.MultipartFile;

public interface BookService {

    BookResponse save(BookRequest request, Authentication connectedUser);
    BookResponse findById(Integer bookId);
    PageResponse<BookResponse> findAllBooks(int page, int size, Authentication connectedUser);
    PageResponse<BookResponse> findAllBooksByOwner(int page, int size, Authentication connectedUser);
    Integer updateShareableStatus(Integer bookId, Authentication connectedUser);
    Integer updateArchivedStatus(Integer bookId, Authentication connectedUser);
    Integer borrowBook(Integer bookId, Authentication connectedUser);
    Integer returnBorrowedBook(Integer bookId, Authentication connectedUser);
    Integer approveReturnBorrowedBook(Integer bookId, Authentication connectedUser);
    void uploadBookCoverPicture(MultipartFile file, Authentication connectedUser, Integer bookId);
    PageResponse<BorrowedBookResponse> findAllBorrowedBooks(int page, int size, Authentication connectedUser);
    PageResponse<BorrowedBookResponse> findAllReturnedBooks(int page, int size, Authentication connectedUser);
    void updateBook(BookRequest request, Authentication connectedUser, Integer bookId, MultipartFile file);
    PageResponse<BookResponse> searchBook(Authentication connectedUser, String keywordSearch, int page, int size);
}
