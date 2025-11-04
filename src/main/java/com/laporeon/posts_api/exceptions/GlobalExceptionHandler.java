package com.laporeon.posts_api.exceptions;

import com.laporeon.posts_api.dto.response.ErrorResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDTO> handleValidationException(MethodArgumentNotValidException ex) {

        Map<String, String> messages = new HashMap<>();

        ex.getBindingResult()
          .getFieldErrors()
          .stream()
          .forEach(error ->
              messages.put(error.getField(), error.getDefaultMessage())
          );

        ErrorResponseDTO dto = buildError(HttpStatus.BAD_REQUEST, messages);

        return ResponseEntity.badRequest().body(dto);
    }

    @ExceptionHandler(PostNotFoundException.class)
    public ResponseEntity<ErrorResponseDTO> handlePostNotFoundException(PostNotFoundException ex) {
        Map<String, String> messages = new HashMap<>();
        messages.put("id", ex.getMessage());

        ErrorResponseDTO error = buildError(HttpStatus.NOT_FOUND, messages);

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    private ErrorResponseDTO buildError(HttpStatus status, Map<String, String> messages) {
        return new ErrorResponseDTO(
                status.value(),
                status.name(),
                messages,
                Instant.now()
        );
    }
}
