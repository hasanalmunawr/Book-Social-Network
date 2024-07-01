package com.hasanalmunawr.book_network.token;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TokenRepository extends JpaRepository<TokenEntity, Integer> {

    Optional<TokenEntity> findByToken(String token);
}
