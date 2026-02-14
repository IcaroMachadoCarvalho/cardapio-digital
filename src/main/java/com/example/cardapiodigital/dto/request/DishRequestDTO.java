package com.example.cardapiodigital.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Schema(description = "Objeto da requisição para criar um prato")
public record DishRequestDTO(
        @Schema(description = "Nome do prato", example = "Hambúrguer Artesanal") @NotBlank(message = "O nome não pode estar em branco") String name,

        @Schema(description = "Descrição detalhada do prato", example = "Hambúrguer com pão brioche, carne 180g e queijo cheddar") @NotBlank(message = "A descrição não pode estar em branco") String description,

        @Schema(description = "Preço do prato em reais", example = "29.90") @NotNull(message = "O preço é obrigatório") @Positive(message = "O preço deve ser positivo") Double price) {
}
