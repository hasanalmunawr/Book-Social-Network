package com.hasanalmunawr.book_network.service;

import com.hasanalmunawr.book_network.book.BookResponse;
import com.hasanalmunawr.book_network.common.PageResponse;
import org.springframework.security.core.Authentication;


public interface WishlistService {

    void add(Authentication currentUser, Integer bookId);

    PageResponse<BookResponse> getAllWishlistByUser(Authentication connectedUser, int size, int page);

    void deleteWishlist(Authentication connectedUser, Integer bookId);
}
