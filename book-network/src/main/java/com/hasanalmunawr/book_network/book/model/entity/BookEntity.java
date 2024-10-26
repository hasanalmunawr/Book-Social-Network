package com.hasanalmunawr.book_network.book.model.entity;

import com.hasanalmunawr.book_network.auth.model.entity.UserEntity;
import com.hasanalmunawr.book_network.common.BaseEntity;
import com.hasanalmunawr.book_network.feedback.module.entity.FeedBackEntity;
import com.hasanalmunawr.book_network.history.model.TransactionHistoryEntity;
import com.hasanalmunawr.book_network.wishlist.model.WishlistEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedBy;

import java.util.List;

@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "books")
public class BookEntity extends BaseEntity {

    private String title;
    private String authorName;
    private String isbn;
    private String synopsis;
    private String bookCover;
    private boolean archived;
    private boolean shareable;

    @CreatedBy
    @Column(nullable = false, updatable = false)
    private String createdBy;


    @ManyToOne
    @JoinColumn(name = "owner_id")
    private UserEntity owner;

    @OneToMany(mappedBy = "book")
    private List<FeedBackEntity> feedbacks;

    @OneToMany(mappedBy = "book")
    private List<TransactionHistoryEntity> histories;

    @ManyToMany(mappedBy = "books")
    private List<WishlistEntity> wishlists;


    @Transient
    public double getRate() {
        if (feedbacks == null || feedbacks.isEmpty()) {
            return 0.0;
        }
        var rate = this.feedbacks.stream()
                .mapToDouble(FeedBackEntity::getNote)
                .average()
                .orElse(0.0);
        double roundedRate = Math.round(rate * 10.0) / 10.0;

        // Return 4.0 if roundedRate is less than 4.5, otherwise return 4.5
        return roundedRate;
    }
}
