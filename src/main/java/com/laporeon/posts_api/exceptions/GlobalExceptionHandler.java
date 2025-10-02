package com.laporeon.posts_api.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<APIErrorResponse> handleValidationException(MethodArgumentNotValidException ex) {

        List<String> errors = ex.getBindingResult()
                                .getFieldErrors()
                                .stream()
                                .map(error -> error.getDefaultMessage())
                                .collect(Collectors.toList());

        APIErrorResponse error = buildError(HttpStatus.BAD_REQUEST, errors);

        return ResponseEntity.badRequest().body(error);
    }

    @ExceptionHandler(PostNotFoundException.class)
    public ResponseEntity<APIErrorResponse> handlePostNotFoundException(PostNotFoundException ex) {

        APIErrorResponse error = buildError(HttpStatus.NOT_FOUND, List.of(ex.getMessage()));

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    private APIErrorResponse buildError(HttpStatus status, List<String> errors) {
        return new APIErrorResponse(
                status.value(),
                status.name(),
                errors,
                Instant.now()
        );
    }
}
