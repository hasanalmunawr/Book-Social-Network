package com.hasanalmunawr.book_network.exception.handler;

import com.hasanalmunawr.book_network.exception.custom.ActivationTokenException;
import com.hasanalmunawr.book_network.exception.custom.OperationNotPermittedException;
import com.hasanalmunawr.book_network.exception.custom.UserExistException;
import jakarta.mail.MessagingException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

import static com.hasanalmunawr.book_network.exception.handler.BusinessErrorCodes.*;
import static org.springframework.http.HttpStatus.*;

@RestControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(LockedException.class)
    public ResponseEntity<ExceptionResponse> handleException(LockedException ex) {
        return ResponseEntity
                .status(UNAUTHORIZED)
                .body(
                        ExceptionResponse.builder()
                                .status("error")
                                .message("Account Locked")
                                .code(UNAUTHORIZED.value())
                                .error(ex.getMessage())
                                .build()
                );
    }

    @ExceptionHandler(DisabledException.class)
    public ResponseEntity<ExceptionResponse> handleException(DisabledException ex) {
        return ResponseEntity
                .status(UNAUTHORIZED)
                .body(
                        ExceptionResponse.builder()
                                .status("error")
                                .message("Account Disabled")
                                .code(UNAUTHORIZED.value())
                                .description("The account is disabled and cannot be accessed.")
                                .error(ex.getMessage())
                                .build()
                );
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ExceptionResponse> handleException() {
        return ResponseEntity
                .status(UNAUTHORIZED)
                .body(
                        ExceptionResponse.builder()
                                .status("error")
                                .message("Unauthorized Access")
                                .code(UNAUTHORIZED.value())
                                .description("Email or password is incorrect.")
                                .error("Invalid credentials provided.")
                                .build()
                );
    }

    @ExceptionHandler(MessagingException.class)
    public ResponseEntity<ExceptionResponse> handleException(MessagingException ex) {
        return ResponseEntity
                .status(INTERNAL_SERVER_ERROR)
                .body(
                        ExceptionResponse.builder()
                                .status("error")
                                .message("Messaging Error")
                                .code(INTERNAL_SERVER_ERROR.value())
                                .description("An error occurred while processing the messaging request.")
                                .error(ex.getMessage())
                                .build()
                );
    }

    @ExceptionHandler(ActivationTokenException.class)
    public ResponseEntity<ExceptionResponse> handleException(ActivationTokenException ex) {
        return ResponseEntity
                .status(BAD_REQUEST)
                .body(
                        ExceptionResponse.builder()
                                .status("error")
                                .message("Invalid Activation Token")
                                .code(BAD_REQUEST.value())
                                .description("The activation token provided is invalid or has expired.")
                                .error(ex.getMessage())
                                .build()
                );
    }

    @ExceptionHandler(OperationNotPermittedException.class)
    public ResponseEntity<ExceptionResponse> handleException(OperationNotPermittedException ex) {
        return ResponseEntity
                .status(BAD_REQUEST)
                .body(
                        ExceptionResponse.builder()
                                .status("error")
                                .message("Operation Not Permitted")
                                .code(BAD_REQUEST.value())
                                .description("You do not have permission to perform this operation.")
                                .error(ex.getMessage())
                                .build()
                );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        List<ExceptionResponse.ValidationError> errors = ex.getBindingResult().getFieldErrors()
                .stream()
                .map(error -> new ExceptionResponse.ValidationError(error.getField(), error.getDefaultMessage()))
                .collect(Collectors.toList());

        return ResponseEntity
                .status(BAD_REQUEST)
                .body(
                        ExceptionResponse.builder()
                                .status("error")
                                .message("Validation Error")
                                .code(BAD_REQUEST.value())
                                .description("Validation failed for one or more fields.")
                                .errors(errors)
                                .build()
                );
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ExceptionResponse> handleException(RuntimeException ex) {
        return ResponseEntity
                .status(INTERNAL_SERVER_ERROR)
                .body(
                        ExceptionResponse.builder()
                                .status("error")
                                .message("Internal Server Error")
                                .code(INTERNAL_SERVER_ERROR.value())
                                .description("An unexpected error occurred.")
                                .error(ex.getMessage())
                                .build()
                );

    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionResponse> handleException(Exception ex) {
        return ResponseEntity
                .status(INTERNAL_SERVER_ERROR)
                .body(
                        ExceptionResponse.builder()
                                .status("error")
                                .message("Internal error, please contact the admin")
                                .code(INTERNAL_SERVER_ERROR.value())
                                .error(ex.getMessage())
                                .build()
                );
    }


}
