package com.example.cardapiodigital.controller;

import com.example.cardapiodigital.dto.request.DishRequestDTO;
import com.example.cardapiodigital.dto.request.DishUpdateDTO;
import com.example.cardapiodigital.dto.response.ApiResponseDTO;
import com.example.cardapiodigital.infrastructure.entity.Dish;
import com.example.cardapiodigital.services.DishService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("api/v1/dishes")
// Swagger accordion
@Tag(name = "Pratos", description = "Gerenciamento de pratos do sistema")
public class DishController {
    private DishService dishService;

    public DishController(DishService dishService) {
        this.dishService = dishService;
    }

    // @valid valida annotation do dto
    @PostMapping
    // @Operation define o resumo e a descrição detalhada do endpoint
    @Operation(summary = "Cadastra um novo prato", description = "Requisição de cadastro de prato")

    // @ApiResponse define o que esperar em cada cenário (sucesso ou erro)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Produto criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados de entrada inválidos"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })

    public ResponseEntity<ApiResponseDTO> createDish(@RequestBody @Valid DishRequestDTO dish) {
        Dish newDish = dishService.salvarPrato(dish);
        // return ResponseEntity.ok().build(); .ok -> status e .build() -> resposta sem
        // corpo

        return ResponseEntity.status(HttpStatus.CREATED).body(
                ApiResponseDTO.success("Prato criado com sucesso!", newDish));
    }

    @GetMapping
    @Operation(summary = "Listagem de pratos", description = "Requisição de listagem de pratos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pratos listados com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados de entrada inválidos"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    public ResponseEntity<ApiResponseDTO> listDishes(
            @RequestParam(required = false) String status,
            @RequestParam(required = false) @Positive Double price) {
        List<Dish> dishes = dishService.listarPratos(status, price);

        if (dishes.isEmpty()) {
            return ResponseEntity.ok(
                    ApiResponseDTO.error("Nenhum prato cadastrado!", null));
        }

        return ResponseEntity.ok(
                ApiResponseDTO.success("Pratos listados com sucesso!", dishes));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Listagem de prato", description = "Requisição de listagem de prato")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Prato listado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados de entrada inválidos"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    public ResponseEntity<ApiResponseDTO> getDishById(@PathVariable Long id) {
        Dish dish = dishService.listarPrato(id);

        return ResponseEntity.ok(
                ApiResponseDTO.success("Prato listado com sucesso!", dish));

        /*
         * Tem essa forma que pega como uma array
         * return dishService.listarPrato(id)
         * .map(dish -> ResponseEntity.ok(
         * ApiResponseDTO.success(dish, "Prato listado com sucesso!")
         * ))
         * .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(
         * ApiResponseDTO.error("Prato não encontrado!", null)
         * ));
         */
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Atualizar um prato", description = "Requisição de atualização de prato")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Prato atualizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados de entrada inválidos"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    public ResponseEntity<ApiResponseDTO> patchDish(@PathVariable Long id,
            @RequestBody @Valid DishUpdateDTO dish) {

        Dish updatedDish = dishService.atualizarPrato(id, dish);

        return ResponseEntity.ok(
                ApiResponseDTO.success("Prato atualizado com sucesso!", updatedDish));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar um prato", description = "Requisição de exclusão de prato")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Prato deletado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados de entrada inválidos"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    public ResponseEntity<ApiResponseDTO> deleteDish(@PathVariable Long id) {
        dishService.deletarPrato(id);
        return ResponseEntity.noContent().build();
    }
}
