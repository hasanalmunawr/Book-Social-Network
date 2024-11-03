package com.hasanalmunawr.book_network.book.repository;

import com.hasanalmunawr.book_network.book.model.entity.BookEntity;
import org.springframework.data.jpa.domain.Specification;

public class BookSpecification {

    public static Specification<BookEntity> withOwnerId(Integer ownerId) {
        return (root, query, criteriaBuilder) -> {
            var ownerJoin = root.join("owner");
            return criteriaBuilder.equal(ownerJoin.get("id"), ownerId);
        };
    }
}
