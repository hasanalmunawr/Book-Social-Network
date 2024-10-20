package com.hasanalmunawr.book_network.book.service.impl;

import com.hasanalmunawr.book_network.book.model.dto.BookRequest;
import com.hasanalmunawr.book_network.book.model.dto.BookResponse;
import com.hasanalmunawr.book_network.book.model.dto.BorrowedBookResponse;
import com.hasanalmunawr.book_network.book.model.entity.BookEntity;
import com.hasanalmunawr.book_network.book.model.mapper.BookMapper;
import com.hasanalmunawr.book_network.book.repository.BookRepository;
import com.hasanalmunawr.book_network.book.service.BookService;
import com.hasanalmunawr.book_network.book.model.dto.PageResponse;
import com.hasanalmunawr.book_network.exception.custom.OperationNotPermittedException;
import com.hasanalmunawr.book_network.storage.FileStorageService;
import com.hasanalmunawr.book_network.history.model.TransactionHistoryEntity;
import com.hasanalmunawr.book_network.history.repository.TransactionHistoryRepository;
import com.hasanalmunawr.book_network.auth.model.entity.UserEntity;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Objects;

import static com.hasanalmunawr.book_network.book.repository.BookSpecification.withOwnerId;

@Service
@RequiredArgsConstructor
@Slf4j
class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final BookMapper bookMapper;
    private final TransactionHistoryRepository historyRepository;
    private final FileStorageService fileStorageService;


    @Override
    public Integer save(BookRequest request, Authentication connectedUser) {
//        UserEntity user = ((UserEntity) connectedUser.getPrincipal());
        BookEntity book = bookMapper.toBook(request);
        book.setCreatedBy(connectedUser.getName());
        return bookRepository.save(book).getId();
    }

    @Override
    public BookResponse findById(Integer bookId) {
        return bookRepository.findById(bookId)
                .map(bookMapper::toBookResponse)
                .orElseThrow(() -> new EntityNotFoundException("No book found with ID:: " + bookId));
    }

    @Override
    public PageResponse<BookResponse> findAllBooks(int page, int size, Authentication connectedUser) {
//        UserEntity user = ((UserEntity) connectedUser.getPrincipal());
        Pageable pageable = PageRequest.of(page, size);
        Page<BookEntity> books = bookRepository.findAllDisplayableBooks(pageable, connectedUser.getName());
        log.info("[BookServiceImpl:FIndAllBooksByOwner] user id {}", connectedUser.getName());
        List<BookResponse> booksResponse = books.stream()
                .map(bookMapper::toBookResponse)
                .toList();

        return new PageResponse<>(
                booksResponse,
                books.getNumber(),
                books.getSize(),
                books.getTotalElements(),
                books.getTotalPages(),
                books.isFirst(),
                books.isLast()
        );
    }

    @Override
    public PageResponse<BookResponse> findAllBooksByOwner(int page, int size, Authentication connectedUser) {
//        UserEntity user = ((UserEntity) connectedUser.getPrincipal());
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdBy").descending());
        Page<BookEntity> books = bookRepository.findAll(withOwnerId(connectedUser.getName()), pageable);
        List<BookResponse> booksResponse = books.stream()
                .map(bookMapper::toBookResponse)
                .toList();
        return new PageResponse<>(
                booksResponse,
                books.getNumber(),
                books.getSize(),
                books.getTotalElements(),
                books.getTotalPages(),
                books.isFirst(),
                books.isLast()
        );
    }

    @Override
    public Integer updateShareableStatus(Integer bookId, Authentication connectedUser) {
        BookEntity book = bookRepository.findById(bookId)
                .orElseThrow(() -> new EntityNotFoundException("No book found with ID:: " + bookId));
//        UserEntity user = ((UserEntity) connectedUser.getPrincipal());
        if (!Objects.equals(book.getCreatedBy(), connectedUser.getName())) {
            throw new OperationNotPermittedException("You cannot update others books shareable status");
        }
        book.setShareable(!book.isShareable());
        bookRepository.save(book);
        return bookId;
    }

    @Override
    public Integer updateArchivedStatus(Integer bookId, Authentication connectedUser) {
        BookEntity book = bookRepository.findById(bookId)
                .orElseThrow(() -> new EntityNotFoundException("No book found with ID:: " + bookId));
//        UserEntity user = ((UserEntity) connectedUser.getPrincipal());
        if (!Objects.equals(book.getCreatedBy(), connectedUser.getName())) {
            throw new OperationNotPermittedException("You cannot update others books archived status");
        }
        book.setArchived(!book.isArchived());
        bookRepository.save(book);
        return bookId;
    }

    @Override
    public Integer borrowBook(Integer bookId, Authentication connectedUser) {
        BookEntity book = bookRepository.findById(bookId)
                .orElseThrow(() -> new EntityNotFoundException("No book found with ID:: " + bookId));
        if (book.isArchived() || !book.isShareable()) {
            throw new OperationNotPermittedException("The requested book cannot be borrowed since it is archived or not shareable");
        }
//        UserEntity user = ((UserEntity) connectedUser.getPrincipal());
        if (Objects.equals(book.getCreatedBy(), connectedUser.getName())) {
            throw new OperationNotPermittedException("You cannot borrow your own book");
        }
        final boolean isAlreadyBorrowedByUser = historyRepository.isAlreadyBorrowedByUser(bookId, connectedUser.getName());
        if (isAlreadyBorrowedByUser) {
            throw new OperationNotPermittedException("You already borrowed this book and it is still not returned or the return is not approved by the owner");
        }

        final boolean isAlreadyBorrowedByOtherUser = historyRepository.isAlreadyBorrowed(bookId);
        if (isAlreadyBorrowedByOtherUser) {
            throw new OperationNotPermittedException("Te requested book is already borrowed");
        }

        TransactionHistoryEntity bookTransactionHistory = TransactionHistoryEntity.builder()
                .userId(connectedUser.getName())
                .book(book)
                .returned(false)
                .returnApproved(false)
                .build();
        return historyRepository.save(bookTransactionHistory).getId();
    }

    @Override
    public Integer returnBorrowedBook(Integer bookId, Authentication connectedUser) {
        BookEntity book = bookRepository.findById(bookId)
                .orElseThrow(() -> new EntityNotFoundException("No book found with ID:: " + bookId));
        if (book.isArchived() || !book.isShareable()) {
            throw new OperationNotPermittedException("The requested book is archived or not shareable");
        }
//        UserEntity user = ((UserEntity) connectedUser.getPrincipal());
        if (Objects.equals(book.getCreatedBy(), connectedUser.getName())) {
            throw new OperationNotPermittedException("You cannot borrow or return your own book");
        }

        TransactionHistoryEntity bookTransactionHistory = historyRepository.findByBookIdAndUserId(bookId, connectedUser.getName())
                .orElseThrow(() -> new OperationNotPermittedException("You did not borrow this book"));

        bookTransactionHistory.setReturned(true);
        return historyRepository.save(bookTransactionHistory).getId();
    }

    @Override
    public Integer approveReturnBorrowedBook(Integer bookId, Authentication connectedUser) {
        BookEntity book = bookRepository.findById(bookId)
                .orElseThrow(() -> new EntityNotFoundException("No book found with ID:: " + bookId));
        if (book.isArchived() || !book.isShareable()) {
            throw new OperationNotPermittedException("The requested book is archived or not shareable");
        }
        UserEntity user = ((UserEntity) connectedUser.getPrincipal());
        if (!Objects.equals(book.getCreatedBy(), connectedUser.getName())) {
            throw new OperationNotPermittedException("You cannot approve the return of a book you do not own");
        }

        TransactionHistoryEntity bookTransactionHistory = historyRepository.findByBookIdAndOwnerId(bookId, connectedUser.getName())
                .orElseThrow(() -> new OperationNotPermittedException("The book is not returned yet. You cannot approve its return"));

        bookTransactionHistory.setReturnApproved(true);
        return historyRepository.save(bookTransactionHistory).getId();
    }

    @Override
    public void uploadBookCoverPicture(MultipartFile file, Authentication connectedUser, Integer bookId) {
        BookEntity book = bookRepository.findById(bookId)
                .orElseThrow(() -> new EntityNotFoundException("No book found with ID:: " + bookId));
//        UserEntity user = ((UserEntity) connectedUser.getPrincipal());
        var profilePicture = fileStorageService.saveFile(file, connectedUser.getName());
        book.setBookCover(profilePicture);
        bookRepository.save(book);
    }

    @Override
    public PageResponse<BorrowedBookResponse> findAllBorrowedBooks(int page, int size, Authentication connectedUser) {
//        UserEntity user = ((UserEntity) connectedUser.getPrincipal());
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        Page<TransactionHistoryEntity> allBorrowedBooks = historyRepository.findAllBorrowedBooks(pageable, connectedUser.getName());
        List<BorrowedBookResponse> booksResponse = allBorrowedBooks.stream()
                .map(bookMapper::toBorrowedBookResponse)
                .toList();
        return new PageResponse<>(
                booksResponse,
                allBorrowedBooks.getNumber(),
                allBorrowedBooks.getSize(),
                allBorrowedBooks.getTotalElements(),
                allBorrowedBooks.getTotalPages(),
                allBorrowedBooks.isFirst(),
                allBorrowedBooks.isLast()
        );
    }

    @Override
    public PageResponse<BorrowedBookResponse> findAllReturnedBooks(int page, int size, Authentication connectedUser) {
//        UserEntity user = ((UserEntity) connectedUser.getPrincipal());
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        Page<TransactionHistoryEntity> allBorrowedBooks = historyRepository.findAllReturnedBooks(pageable, connectedUser.getName());
        List<BorrowedBookResponse> booksResponse = allBorrowedBooks.stream()
                .map(bookMapper::toBorrowedBookResponse)
                .toList();
        return new PageResponse<>(
                booksResponse,
                allBorrowedBooks.getNumber(),
                allBorrowedBooks.getSize(),
                allBorrowedBooks.getTotalElements(),
                allBorrowedBooks.getTotalPages(),
                allBorrowedBooks.isFirst(),
                allBorrowedBooks.isLast()
        );
    }

    @Override
    @Transactional
    public void updateBook(BookRequest request, Authentication connectedUser, Integer bookId, MultipartFile file) {
        BookEntity book = bookMapper.toBook(request);
        BookEntity bookEntity = bookRepository.findById(bookId)
                .orElseThrow(() -> new EntityNotFoundException("No book found with ID:: " + bookId));
        String pathFile = fileStorageService.saveFile(file, connectedUser.getName());
        bookEntity.setBookCover(pathFile);
        bookEntity.setUpdatedBy(connectedUser.getName());
        bookRepository.save(bookEntity);
    }

    @Override
    public PageResponse<BookResponse> searchBook(Authentication connectedUser, String keywordSearch, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        Page<BookEntity> books = bookRepository.findByTitleContainingIgnoreCase(keywordSearch, pageable);
        List<BookResponse> booksResponse = books.stream()
                .map(bookMapper::toBookResponse)
                .toList();
        return new PageResponse<>(
                booksResponse,
                books.getNumber(),
                books.getSize(),
                books.getTotalElements(),
                books.getTotalPages(),
                books.isFirst(),
                books.isLast()
        );
    }
}
