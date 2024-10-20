package com.hasanalmunawr.book_network.history.model;

import com.hasanalmunawr.book_network.book.model.entity.BookEntity;
import com.hasanalmunawr.book_network.common.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "histories")
public class TransactionHistoryEntity extends BaseEntity {

//    @ManyToOne
//    @JoinColumn(name = "user_id")
//    private UserEntity user;

    @Column(name = "user_id")
    private String userId;
    @ManyToOne
    @JoinColumn(name = "book_id")
    private BookEntity book;
    private boolean returned;
    private boolean returnApproved;

}
