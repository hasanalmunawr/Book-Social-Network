package com.hasanalmunawr.book_network.service;

import com.hasanalmunawr.book_network.book.BookRequest;
import com.hasanalmunawr.book_network.book.BookResponse;
import com.hasanalmunawr.book_network.book.BorrowedBookResponse;
import com.hasanalmunawr.book_network.common.PageResponse;
import org.springframework.security.core.Authentication;
import org.springframework.web.multipart.MultipartFile;

public interface BookService {

    Integer save(BookRequest request, Authentication connectedUser);
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



}
