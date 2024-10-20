package com.hasanalmunawr.book_network.feedback.repository;

import com.hasanalmunawr.book_network.feedback.module.entity.FeedBackEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface FeedBackRepository extends JpaRepository<FeedBackEntity, Integer> {

    @Query("""
                        SELECT feedback
                        FROM FeedBackEntity  feedback
                        WHERE feedback.book.id = :bookId
            """)
    Page<FeedBackEntity> findAllByBookId(@Param("bookId") Integer bookId, Pageable pageable);

}
