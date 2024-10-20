package com.hasanalmunawr.book_network.wishlist.service.impl;

import com.hasanalmunawr.book_network.book.model.entity.BookEntity;
import com.hasanalmunawr.book_network.book.model.mapper.BookMapper;
import com.hasanalmunawr.book_network.book.repository.BookRepository;
import com.hasanalmunawr.book_network.book.model.dto.BookResponse;
import com.hasanalmunawr.book_network.book.model.dto.PageResponse;
import com.hasanalmunawr.book_network.wishlist.model.WishlistEntity;
import com.hasanalmunawr.book_network.wishlist.repository.WishlistRepository;
import com.hasanalmunawr.book_network.wishlist.service.WishlistService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WishlistServiceImpl implements WishlistService {

    private final WishlistRepository wishlistRepository;
    private final BookMapper bookMapper;
    private final BookRepository bookRepository;

    @Override
    public void add(Authentication currentUser, Integer bookId) {
        BookEntity bookEntity = bookRepository.findById(bookId).orElseThrow(() -> new EntityNotFoundException("Book not found"));
        WishlistEntity wishlistEntity = new WishlistEntity();
        wishlistEntity.setCreatedBy(currentUser.getName());
        wishlistEntity.setUserId(currentUser.getName());
        wishlistEntity.getBooks().add(bookEntity);
        wishlistRepository.save(wishlistEntity);
    }

    @Override
    public PageResponse<BookResponse> getAllWishlistByUser(Authentication connectedUser, int size, int page) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdBy").descending());
        Page<BookEntity> books = wishlistRepository.findAllWishlistByUser(pageable, connectedUser.getName());
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
    @Transactional
    public void deleteWishlist(Authentication connectedUser, Integer bookId) {
        WishlistEntity wishlistEntity = wishlistRepository.findByUserId(connectedUser.getName()).orElseThrow(() -> new EntityNotFoundException("Wishlist not found"));

        BookEntity bookEntity = bookRepository.findById(bookId)
                .orElseThrow(() -> new EntityNotFoundException("Book not found"));

        if (wishlistEntity.getBooks().contains(bookEntity)) {
            wishlistEntity.getBooks().remove(bookEntity);
            wishlistRepository.save(wishlistEntity);
        } else {
            throw new EntityNotFoundException("Book not found in wishlist");
        }
    }
}
