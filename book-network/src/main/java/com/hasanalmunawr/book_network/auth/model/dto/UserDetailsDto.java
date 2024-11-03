package com.hasanalmunawr.book_network.auth.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class UserDetailsDto {

    private String username;
    private String email;
    private String role;
}
