package com.example.cardapiodigital.infrastructure.exception;

import com.example.cardapiodigital.dto.response.ApiResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponseDTO> handleException(Exception ex) {
        return ResponseEntity.status(500).body(ApiResponseDTO.error("Erro interno no servidor", null));
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponseDTO> handleResourceNotFoundException(ResourceNotFoundException ex) {
        return ResponseEntity.status(404).body(ApiResponseDTO.error(ex.getMessage(), null));
        // return
        // ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResponseDTO.error(ex.getMessage(),
        // null));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class) // Define que esse método é chamado nessa exceção
    public ResponseEntity<ApiResponseDTO<Map<String, String>>> handleValidationException(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();

        // Percorre todos os erros encontrados no DTO
        ex.getBindingResult().getFieldErrors().forEach(error -> {
            errors.put(error.getField(), error.getDefaultMessage());
        });

        ApiResponseDTO<Map<String, String>> response = ApiResponseDTO.error("Dados de entrada inválidos",
                errors);
        // badRequest -> 400
        return ResponseEntity.badRequest().body(response);
    }
}
