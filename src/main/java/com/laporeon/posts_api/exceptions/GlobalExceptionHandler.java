package com.laporeon.posts_api.exceptions;

import com.laporeon.posts_api.dto.response.ErrorResponseDTO;
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
    public ResponseEntity<ErrorResponseDTO> handleValidationException(MethodArgumentNotValidException ex) {

        List<String> messages = ex.getBindingResult()
                                .getFieldErrors()
                                .stream()
                                .map(error -> error.getDefaultMessage())
                                .collect(Collectors.toList());

        ErrorResponseDTO dto = buildError(HttpStatus.BAD_REQUEST, messages);

        return ResponseEntity.badRequest().body(dto);
    }

    @ExceptionHandler(PostNotFoundException.class)
    public ResponseEntity<ErrorResponseDTO> handlePostNotFoundException(PostNotFoundException ex) {

        ErrorResponseDTO error = buildError(HttpStatus.NOT_FOUND, List.of(ex.getMessage()));

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    private ErrorResponseDTO buildError(HttpStatus status, List<String> messages) {
        return new ErrorResponseDTO(
                status.value(),
                status.name(),
                messages,
                Instant.now()
        );
    }
}
