package com.hasanalmunawr.book_network.exception.handler;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ExceptionResponse {

    private String status;
    private String message;
    private Integer code;
    private String description;
    private String error;
    private List<ValidationError> errors;

    @Data
    @AllArgsConstructor
    public static class ValidationError {
        private String field;
        private String error;
    }
}
