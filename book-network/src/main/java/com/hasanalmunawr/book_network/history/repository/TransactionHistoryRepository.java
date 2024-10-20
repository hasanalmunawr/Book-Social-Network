package com.hasanalmunawr.book_network.history.repository;

import com.hasanalmunawr.book_network.history.model.TransactionHistoryEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TransactionHistoryRepository extends JpaRepository<TransactionHistoryEntity, Integer> {


    @Query("""
            SELECT (COUNT (*) > 0) AS isBorrowed
            FROM TransactionHistoryEntity th
            WHERE th.userId = :userId
            AND th.book.id = :bookId
            AND th.returnApproved = false 
            """)
    boolean isAlreadyBorrowedByUser(@Param("bookId") Integer bookId, @Param("userId") String userId);

    @Query("""
            SELECT (COUNT (*) > 0) AS isBorrowed
            FROM TransactionHistoryEntity th
            WHERE th.book.id = :bookId
            AND th.returnApproved = false
            """)
    boolean isAlreadyBorrowed(@Param("bookId") Integer bookId);

    @Query("""
            SELECT history
            FROM TransactionHistoryEntity history
            WHERE history.userId = :userId
            AND history.book.id = :bookId
            AND history.returnApproved = false
            AND history.returned = false 
            """)
    Optional<TransactionHistoryEntity> findByBookIdAndUserId(@Param("bookId") Integer bookId, @Param("userId") String userId);

    @Query("""
            SELECT history
            FROM TransactionHistoryEntity history
            WHERE history.book.createdBy = :userId
            AND history.book.id = :bookId
            AND history.returned = true
            AND history.returnApproved = false
            """)
    Optional<TransactionHistoryEntity> findByBookIdAndOwnerId(@Param("bookId") Integer bookId, @Param("userId") String userId);

    @Query("""
            SELECT history
            FROM TransactionHistoryEntity history
            WHERE history.userId = :userId
            """)
    Page<TransactionHistoryEntity> findAllBorrowedBooks(Pageable pageable, String userId);

    @Query("""
            SELECT history
            FROM TransactionHistoryEntity history
            WHERE history.book.createdBy = :userId
            """)
    Page<TransactionHistoryEntity> findAllReturnedBooks(Pageable pageable, String userId);

}
