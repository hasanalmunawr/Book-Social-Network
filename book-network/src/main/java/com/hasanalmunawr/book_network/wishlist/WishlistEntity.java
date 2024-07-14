package com.hasanalmunawr.book_network.wishlist;

import com.hasanalmunawr.book_network.book.BookEntity;
import com.hasanalmunawr.book_network.common.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "wishlists")
public class WishlistEntity extends BaseEntity {

    private String name;

    private String userId;

    @ManyToMany
    @JoinTable(
            name = "wishlist_books",
            joinColumns = @JoinColumn(name = "wishlist_id"),
            inverseJoinColumns = @JoinColumn(name = "book_id")
    )
    private List<BookEntity> books;
}
