package com.library.exception;

import com.library.api.ResponseError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BookNotFoundException.class)
    public ResponseEntity<ResponseError> handleBookNotFoundException(BookNotFoundException ex) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        ResponseError errorResponse = new ResponseError.Builder()
                .statusCode(status.value())
                .responseMessage(ex.getMessage())
                .timestamp(LocalDateTime.now())
                .build();
        return ResponseEntity.status(status).body(errorResponse);
    }

    @ExceptionHandler(NoAvailableCopiesException.class)
    public ResponseEntity<ResponseError> handleNoAvailableCopiesException(NoAvailableCopiesException ex) {
        HttpStatus status = HttpStatus.CONFLICT;
        ResponseError errorResponse = new ResponseError.Builder()
                .statusCode(status.value())
                .responseMessage(ex.getMessage())
                .timestamp(LocalDateTime.now())
                .build();
        return ResponseEntity.status(status).body(errorResponse);
    }

    // Add other exception handlers as needed
}
