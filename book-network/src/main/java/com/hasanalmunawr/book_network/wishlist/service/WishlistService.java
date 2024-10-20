package com.hasanalmunawr.book_network.wishlist.service;

import com.hasanalmunawr.book_network.book.model.dto.BookResponse;
import com.hasanalmunawr.book_network.book.model.dto.PageResponse;
import org.springframework.security.core.Authentication;


public interface WishlistService {

    void add(Authentication currentUser, Integer bookId);

    PageResponse<BookResponse> getAllWishlistByUser(Authentication connectedUser, int size, int page);

    void deleteWishlist(Authentication connectedUser, Integer bookId);
}
