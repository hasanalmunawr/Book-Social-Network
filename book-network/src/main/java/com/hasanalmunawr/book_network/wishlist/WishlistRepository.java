package com.hasanalmunawr.book_network.wishlist;

import com.hasanalmunawr.book_network.book.BookEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.core.Authentication;

import java.util.Optional;

public interface WishlistRepository extends JpaRepository<WishlistEntity, Integer> {

    @Query("""
            SELECT wish FROM WishlistEntity wish
            WHERE wish.userId = :userId
            """)
    Page<BookEntity> findAllWishlistByUser(Pageable pageable, String userId);

    Optional<WishlistEntity> findByUserId(String userId);


}
