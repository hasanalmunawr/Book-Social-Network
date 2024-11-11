package com.hasanalmunawr.book_network.common.response;

import com.hasanalmunawr.book_network.exception.handler.ExceptionResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class ApiResponse<T> {

    private Boolean success;
    private String message;
    private T data;


}
