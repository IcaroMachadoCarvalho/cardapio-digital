package com.example.cardapiodigital.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Positive;

@Schema(description = "Objeto da requisição para atualizar campos de um prato")
public record DishUpdateDTO(
        @Schema(description = "Nome do prato", example = "Hambúrguer Artesanal") String name,
        @Schema(description = "Descrição detalhada do prato", example = "Hambúrguer com pão brioche, carne 180g e queijo cheddar") String description,
        @Schema(description = "Preço do prato em reais", example = "29.90") @Positive(message = "O preço deve ser positivo") Double price,
        String status) {
}
