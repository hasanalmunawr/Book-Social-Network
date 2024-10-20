package com.hasanalmunawr.book_network.book.model.mapper;

import com.hasanalmunawr.book_network.book.model.dto.BookRequest;
import com.hasanalmunawr.book_network.book.model.dto.BookResponse;
import com.hasanalmunawr.book_network.book.model.dto.BorrowedBookResponse;
import com.hasanalmunawr.book_network.book.model.entity.BookEntity;
import com.hasanalmunawr.book_network.storage.FileUtils;
import com.hasanalmunawr.book_network.history.model.TransactionHistoryEntity;
import org.springframework.stereotype.Service;

@Service
public class BookMapper {

    public BookEntity toBook(BookRequest request) {
        return BookEntity.builder()
                .title(request.title())
                .isbn(request.isbn())
                .authorName(request.authorName())
                .synopsis(request.synopsis())
                .archived(false)
                .shareable(request.shareable())
                .build();
    }

    public BookResponse toBookResponse(BookEntity book) {
        return BookResponse.builder()
                .id(book.getId())
                .title(book.getTitle())
                .authorName(book.getAuthorName())
                .isbn(book.getIsbn())
                .synopsis(book.getSynopsis())
                .rate(book.getRate())
                .archived(book.isArchived())
                .shareable(book.isShareable())
//                .owner(book.getOwner().getFullName())
                .cover(FileUtils.readFileFromLocation(book.getBookCover()))
                .build();
    }

    public BorrowedBookResponse toBorrowedBookResponse(TransactionHistoryEntity history) {
        return BorrowedBookResponse.builder()
                .id(history.getBook().getId())
                .title(history.getBook().getTitle())
                .authorName(history.getBook().getAuthorName())
                .isbn(history.getBook().getIsbn())
                .rate(history.getBook().getRate())
                .returned(history.isReturned())
                .returnApproved(history.isReturnApproved())
                .build();
    }
}
