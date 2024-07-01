package com.hasanalmunawr.book_network.book;

import org.springframework.data.jpa.domain.Specification;

public class BookSpecification {

    public static Specification<BookEntity> withOwnerId(Integer ownerId) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("owner").get("id"), ownerId);
    }
}
