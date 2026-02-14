package com.example.cardapiodigital.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

//precisa do getters e construtores para enviar via responseEntity para a serialização  
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponseDTO<T> {
    private boolean success;
    private String message;
    private T data;

    // Métodos estáticos auxiliares para facilitar o uso
    public static <T> ApiResponseDTO<T> success(String message, T data) {
        return new ApiResponseDTO<>(true, message, data);
    }

    public static <T> ApiResponseDTO<T> error(String message, T error) {
        return new ApiResponseDTO<>(false, message, error);
    }
}
