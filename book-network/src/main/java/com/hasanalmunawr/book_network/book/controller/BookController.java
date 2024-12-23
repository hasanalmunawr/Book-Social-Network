package com.hasanalmunawr.book_network.book.controller;

import com.hasanalmunawr.book_network.book.model.dto.BookRequest;
import com.hasanalmunawr.book_network.book.model.dto.BookResponse;
import com.hasanalmunawr.book_network.book.model.dto.BorrowedBookResponse;
import com.hasanalmunawr.book_network.book.model.dto.PageResponse;
import com.hasanalmunawr.book_network.book.service.BookService;
import com.hasanalmunawr.book_network.common.response.ApiResponse;
import com.hasanalmunawr.book_network.wishlist.service.WishlistService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("books")
@RequiredArgsConstructor
@Tag(name = "Book")
@Slf4j
public class BookController {

    private final BookService service;
    private final WishlistService wishlistService;

    @PostMapping
    public ResponseEntity<ApiResponse<BookResponse>> saveBook(
            @Valid @RequestBody BookRequest request,
            Authentication connectedUser
    ) {
        BookResponse bookResponse = service.save(request, connectedUser);
        ApiResponse<BookResponse> response = new ApiResponse<>(true, "Book Success Create", bookResponse);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{book-id}")
    public ResponseEntity<BookResponse> findBookById(
            @PathVariable("book-id") Integer bookId
    ) {
        return ResponseEntity.ok(service.findById(bookId));
    }

    @GetMapping
    public ResponseEntity<PageResponse<BookResponse>> findAllBooks(
            @RequestParam(name = "page", defaultValue = "0", required = false) int page,
            @RequestParam(name = "size", defaultValue = "10", required = false) int size,
            Authentication connectedUser
    ) {
        log.info("[BookController:findAllBooks] id user {}", connectedUser.getName());
        return ResponseEntity.ok(service.findAllBooks(page, size, connectedUser));
    }

    @GetMapping("/owner")
    public ResponseEntity<PageResponse<BookResponse>> findAllBooksByOwner(
            @RequestParam(name = "page", defaultValue = "0", required = false) int page,
            @RequestParam(name = "size", defaultValue = "10", required = false) int size,
            Authentication connectedUser
    ) {
        return ResponseEntity.ok(service.findAllBooksByOwner(page, size, connectedUser));
    }

    @GetMapping("/borrowed")
    public ResponseEntity<PageResponse<BorrowedBookResponse>> findAllBorrowedBooks(
            @RequestParam(name = "page", defaultValue = "0", required = false) int page,
            @RequestParam(name = "size", defaultValue = "10", required = false) int size,
            Authentication connectedUser
    ) {
        return ResponseEntity.ok(service.findAllBorrowedBooks(page, size, connectedUser));
    }

    @GetMapping("/returned")
    public ResponseEntity<PageResponse<BorrowedBookResponse>> findAllReturnedBooks(
            @RequestParam(name = "page", defaultValue = "0", required = false) int page,
            @RequestParam(name = "size", defaultValue = "10", required = false) int size,
            Authentication connectedUser
    ) {
        return ResponseEntity.ok(service.findAllReturnedBooks(page, size, connectedUser));
    }

    @PatchMapping("/shareable/{book-id}")
    public ResponseEntity<Integer> updateShareableStatus(
            @PathVariable("book-id") Integer bookId,
            Authentication connectedUser
    ) {
        return ResponseEntity.ok(service.updateShareableStatus(bookId, connectedUser));
    }

    @PatchMapping("/archived/{book-id}")
    public ResponseEntity<Integer> updateArchivedStatus(
            @PathVariable("book-id") Integer bookId,
            Authentication connectedUser
    ) {
        return ResponseEntity.ok(service.updateArchivedStatus(bookId, connectedUser));
    }

    @PostMapping("/borrow/{book-id}")
    public ResponseEntity<Integer> borrowBook(
            @PathVariable("book-id") Integer bookId,
            Authentication connectedUser
    ) {
        return ResponseEntity.ok(service.borrowBook(bookId, connectedUser));
    }

    @PatchMapping("/borrow/return/{book-id}")
    public ResponseEntity<Integer> returnBorrowBook(
            @PathVariable("book-id") Integer bookId,
            Authentication connectedUser
    ) {
        return ResponseEntity.ok(service.returnBorrowedBook(bookId, connectedUser));
    }

    @PatchMapping("/borrow/return/approve/{book-id}")
    public ResponseEntity<Integer> approveReturnBorrowBook(
            @PathVariable("book-id") Integer bookId,
            Authentication connectedUser
    ) {
        return ResponseEntity.ok(service.approveReturnBorrowedBook(bookId, connectedUser));
    }

    @PostMapping(value = "/cover/{book-id}", consumes = "multipart/form-data")
    public ResponseEntity<?> uploadBookCoverPicture(
            @PathVariable("book-id") Integer bookId,
            @Parameter()
            @RequestPart("file") MultipartFile file,
            Authentication connectedUser
    ) {
        service.uploadBookCoverPicture(file, connectedUser, bookId);
        return ResponseEntity.accepted().build();
    }

    @PatchMapping(value = "/update/{book-id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void updateBook(
            @PathVariable("book-id") Integer bookId,
            BookRequest request,
            Authentication connectedUser,
            @RequestPart("file") MultipartFile file
    ) {
        service.updateBook(request, connectedUser, bookId, file);
    }

    @PostMapping(value = "/wishlist/{book-id}")
    public void addWishlist(
            @PathVariable("book-id") Integer bookId,
            Authentication connectedUser
    ) {
        wishlistService.add(connectedUser, bookId);
    }

    @GetMapping(value = "/wishlist")
    public ResponseEntity<PageResponse<BookResponse>> getAllWishlistByUser(
            @RequestParam(name = "page", defaultValue = "0", required = false) int page,
            @RequestParam(name = "size", defaultValue = "10", required = false) int size,
            Authentication connectedUser
    ) {
       return ResponseEntity.ok(wishlistService.getAllWishlistByUser(connectedUser, size, page));
    }

    @DeleteMapping("/wishlist/{book-id}")
    public void deleteWishlist(
            @PathVariable("book-id") Integer bookId,
            Authentication connectedUser
    ) {
        wishlistService.deleteWishlist(connectedUser, bookId);
    }

    @GetMapping("/search")
    public ResponseEntity<PageResponse<BookResponse>> searchBook(
            Authentication connectedUser,
            @RequestParam(value = "keywordSearch", required = false) String keywordSearch,
             @RequestParam(name = "page", defaultValue = "0", required = false) int page,
    @RequestParam(name = "size", defaultValue = "10", required = false) int size

    ) {
        return ResponseEntity.ok(service.searchBook(connectedUser, keywordSearch, page, size));
    }
}
