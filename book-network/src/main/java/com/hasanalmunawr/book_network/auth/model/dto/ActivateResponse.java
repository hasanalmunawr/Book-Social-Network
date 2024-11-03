package com.hasanalmunawr.book_network.auth.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class ActivateResponse {

    private boolean success;
    private String message;
}
