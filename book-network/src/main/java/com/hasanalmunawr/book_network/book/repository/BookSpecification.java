package com.hasanalmunawr.book_network.book.repository;

import com.hasanalmunawr.book_network.book.model.entity.BookEntity;
import org.springframework.data.jpa.domain.Specification;

public class BookSpecification {

    public static Specification<BookEntity> withOwnerId(String ownerId) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("createdBy"), ownerId);
    }
}
