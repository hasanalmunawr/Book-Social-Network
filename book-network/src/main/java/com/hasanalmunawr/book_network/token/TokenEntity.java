package com.hasanalmunawr.book_network.token;

import com.hasanalmunawr.book_network.user.UserEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
//@Entity
public class TokenEntity {

    @Id
    @GeneratedValue
    private Integer id;

    @Column(unique = true)
    private String token;

    @Column(name = "created_at")
    private LocalDateTime createdAt;
    @Column(name = "expires_at")
    private LocalDateTime expiresAt;
    @Column(name = "validated_at")
    private LocalDateTime validatedAt;

//    @ManyToOne
//    @JoinColumn(name = "userId", nullable = false)
//    private UserEntity user;
}
