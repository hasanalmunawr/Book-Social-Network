package com.hasanalmunawr.book_network.feedback;

import com.hasanalmunawr.book_network.book.BookEntity;
import com.hasanalmunawr.book_network.common.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedBy;

@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "feedbacks")
public class FeedBackEntity extends BaseEntity {

    @Column
    private Double note;
    private String comment;
    @ManyToOne
    @JoinColumn(name = "book_id")
    private BookEntity book;

    @CreatedBy
    @Column(nullable = false, updatable = false)
    private Integer createdBy;
}
