package com.hasanalmunawr.book_network.book;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<BookEntity, Integer>, JpaSpecificationExecutor<BookEntity> {

    @Query("""
            SELECT b FROM BookEntity b
            WHERE b.archived = false 
            AND b.shareable = true 
            AND b.owner.id  != :userId
            """)
    Page<BookEntity> findAllDisplayableBooks(Pageable pageable, Integer userId);
}
