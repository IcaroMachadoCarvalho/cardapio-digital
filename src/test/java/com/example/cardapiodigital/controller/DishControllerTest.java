package com.example.cardapiodigital.controller;

import com.example.cardapiodigital.dto.request.DishRequestDTO;
import com.example.cardapiodigital.dto.request.DishUpdateDTO;
import com.example.cardapiodigital.infrastructure.entity.Dish;
import com.example.cardapiodigital.infrastructure.exception.BadRequestException;
import com.example.cardapiodigital.infrastructure.exception.ResourceNotFoundException;
import com.example.cardapiodigital.services.DishService;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(DishController.class)
public class DishControllerTest {

        @Autowired
        private MockMvc mockMvc;

        @MockBean
        private DishService dishService;

        @Test
        @DisplayName("Deve criar prato com sucesso")
        public void testSaveDish() throws Exception {

                Dish savedDish = new Dish();
                savedDish.setId(1L);
                savedDish.setNome("Prato");
                savedDish.setDescricao("Descrição detalhada do prato");
                savedDish.setPreco(BigDecimal.valueOf(10));
                savedDish.setStatus("DISPONÍVEL");

                when(dishService.salvarPrato(any(DishRequestDTO.class))).thenReturn(savedDish);

                mockMvc.perform(post("/api/v1/dishes")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(
                                                "{\"name\": \"Prato\", \"description\": \"Descrição detalhada do prato\", \"price\": 10.00}"))
                                .andExpect(status().isCreated())
                                .andExpect(jsonPath("$.success").value(true))
                                .andExpect(jsonPath("$.message").value("Prato criado com sucesso!"))
                                .andExpect(jsonPath("$.data.id").value(1L))
                                .andExpect(jsonPath("$.data.nome").value("Prato"))
                                .andExpect(jsonPath("$.data.descricao").value("Descrição detalhada do prato"));
        }

        @Test
        @DisplayName("Deve retonar mensagem de erro com envio campos faltando")
        public void testSaveDishException() throws Exception {

                mockMvc.perform(post("/api/v1/dishes")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(
                                                "{\"name\": \"Prato\", \"description\": \"Descrição detalhada do prato\"}"))
                                .andExpect(status().isBadRequest())
                                .andExpect(jsonPath("$.success").value(false))
                                .andExpect(jsonPath("$.message").value("Dados de entrada inválidos"))
                                .andExpect(jsonPath("$.data").isNotEmpty());
        }

        @Test
        @DisplayName("Deve retonar lista de pratos sem filtros")
        public void testListDishes() throws Exception {

                Dish savedDish = new Dish();
                savedDish.setId(1L);
                savedDish.setNome("Prato");
                savedDish.setDescricao("Descrição detalhada do prato");
                savedDish.setPreco(BigDecimal.valueOf(10));
                savedDish.setStatus("DISPONÍVEL");

                when(dishService.listarPratos(null, null)).thenReturn(List.of(savedDish));

                mockMvc.perform(get("/api/v1/dishes"))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.success").value(true))
                                .andExpect(jsonPath("$.message").value("Pratos listados com sucesso!"))
                                .andExpect(jsonPath("$.data").isArray());
        }

        @Test
        @DisplayName("Deve retornar lista de pratos filtrada por status")
        public void testListDishesWithStatus() throws Exception {
                Dish savedDish = new Dish();
                savedDish.setId(2L);
                savedDish.setNome("Salada");
                savedDish.setDescricao("Salada fresca");
                savedDish.setPreco(BigDecimal.valueOf(15));
                savedDish.setStatus("DISPONÍVEL");

                when(dishService.listarPratos("DISPONÍVEL", null)).thenReturn(List.of(savedDish));

                mockMvc.perform(get("/api/v1/dishes")
                                .param("status", "DISPONÍVEL"))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.success").value(true))
                                .andExpect(jsonPath("$.message").value("Pratos listados com sucesso!"))
                                .andExpect(jsonPath("$.data").isArray())
                                .andExpect(jsonPath("$.data[0].status").value("DISPONÍVEL"));
        }

        @Test
        @DisplayName("Deve retornar lista de pratos filtrada por preço")
        public void testListDishesWithPrice() throws Exception {
                Dish savedDish = new Dish();
                savedDish.setId(3L);
                savedDish.setNome("Lasanha");
                savedDish.setDescricao("Lasanha caseira");
                savedDish.setPreco(BigDecimal.valueOf(25));
                savedDish.setStatus("INDISPONÍVEL");

                when(dishService.listarPratos(null, 20.0)).thenReturn(List.of(savedDish));

                mockMvc.perform(get("/api/v1/dishes")
                                .param("price", "20"))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.success").value(true))
                                .andExpect(jsonPath("$.message").value("Pratos listados com sucesso!"))
                                .andExpect(jsonPath("$.data").isArray())
                                .andExpect(jsonPath("$.data[0].preco").value(25));
        }

        @Test
        @DisplayName("Deve retornar lista de pratos filtrada por status e preço")
        public void testListDishesWithStatusAndPrice() throws Exception {
                Dish savedDish = new Dish();
                savedDish.setId(4L);
                savedDish.setNome("Pizza");
                savedDish.setDescricao("Pizza de pepperoni");
                savedDish.setPreco(BigDecimal.valueOf(30));
                savedDish.setStatus("DISPONÍVEL");

                when(dishService.listarPratos("DISPONÍVEL", 25.0)).thenReturn(List.of(savedDish));

                mockMvc.perform(get("/api/v1/dishes")
                                .param("status", "DISPONÍVEL")
                                .param("price", "25"))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.success").value(true))
                                .andExpect(jsonPath("$.message").value("Pratos listados com sucesso!"))
                                .andExpect(jsonPath("$.data").isArray())
                                .andExpect(jsonPath("$.data[0].status").value("DISPONÍVEL"))
                                .andExpect(jsonPath("$.data[0].preco").value(30));
        }

        // falta o test listat pratos com parametros

        @Test
        @DisplayName("Deve retonar mensagem de erro que não tem prato cadastrado")
        public void testListDishesException() throws Exception {

                when(dishService.listarPratos(null, null)).thenReturn(List.of());

                mockMvc.perform(get("/api/v1/dishes"))
                                .andExpect(status().isNotFound())
                                .andExpect(jsonPath("$.success").value(false))
                                .andExpect(jsonPath("$.message").value("Nenhum prato cadastrado!"))
                                .andExpect(jsonPath("$.data").isEmpty());
        }

        @Test
        @DisplayName("Deve retonar prato com sucesso pelo id")
        public void testFindDish() throws Exception {

                Dish savedDish = new Dish();
                savedDish.setId(1L);
                savedDish.setNome("Prato");
                savedDish.setDescricao("Descrição detalhada do prato");
                savedDish.setPreco(BigDecimal.valueOf(10));
                savedDish.setStatus("DISPONÍVEL");

                dishService.salvarPrato(new DishRequestDTO("Prato", "Descrição detalhada do prato", 10.00));

                when(dishService.listarPrato(1L)).thenReturn(savedDish);

                mockMvc.perform(get("/api/v1/dishes/1"))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.success").value(true))
                                .andExpect(jsonPath("$.message").value("Prato listado com sucesso!"))
                                .andExpect(jsonPath("$.data.id").value(1L))
                                .andExpect(jsonPath("$.data.nome").value("Prato"))
                                .andExpect(jsonPath("$.data.descricao").value("Descrição detalhada do prato"));
        }

        @Test
        @DisplayName("Deve retonar mensagem de erro que não tem prato cadastrado pelo id")
        public void testFindDishException() throws Exception {

                when(dishService.listarPrato(1L)).thenThrow(new ResourceNotFoundException("Prato não encontrado!"));

                mockMvc.perform(get("/api/v1/dishes/1"))
                                .andExpect(status().isNotFound())
                                .andExpect(jsonPath("$.success").value(false))
                                .andExpect(jsonPath("$.message").value("Prato não encontrado!"))
                                .andExpect(jsonPath("$.data").isEmpty());
        }

        @Test
        @DisplayName("Deve atualizar prato com sucesso")
        public void testUpdateDish() throws Exception {

                Dish dish = new Dish();
                dish.setId(1L);
                dish.setNome("Prato");
                dish.setDescricao("Descrição detalhada do prato");
                dish.setPreco(BigDecimal.valueOf(10.00));

                when(dishService.atualizarPrato(eq(1L), any(DishUpdateDTO.class)))
                                .thenReturn(dish);

                mockMvc.perform(patch("/api/v1/dishes/1")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("""
                                                {
                                                    "name": "Prato",
                                                    "description": "Descrição detalhada do prato",
                                                    "price": 10.00
                                                }
                                                """))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.success").value(true))
                                .andExpect(jsonPath("$.message").value("Prato atualizado com sucesso!"))
                                .andExpect(jsonPath("$.data.id").value(1L))
                                .andExpect(jsonPath("$.data.nome").value("Prato"))
                                .andExpect(jsonPath("$.data.descricao").value("Descrição detalhada do prato"));
        }

        @Test
        @DisplayName("Deve retonar mensagem de erro ao atualizar prato")
        public void testUpdateDishException() throws Exception {

                when(dishService.atualizarPrato(eq(1L), any(DishUpdateDTO.class)))
                                .thenThrow(new ResourceNotFoundException("Prato não encontrado!"));

                mockMvc.perform(patch("/api/v1/dishes/1")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("""
                                                {
                                                    "name": "Prato",
                                                    "description": "Descrição detalhada do prato",
                                                    "price": 10.00
                                                }
                                                """))
                                .andExpect(status().isNotFound())
                                .andExpect(jsonPath("$.success").value(false))
                                .andExpect(jsonPath("$.message").value("Prato não encontrado!"))
                                .andExpect(jsonPath("$.data").isEmpty());
        }

        @Test
        @DisplayName("Deve lançar erro se não houver campo para atualizar o prato")
        public void testUpdateDishException2() throws Exception {

                when(dishService.atualizarPrato(eq(1L), any(DishUpdateDTO.class)))
                                .thenThrow(new BadRequestException("Nenhum campo informado para a atualização"));

                mockMvc.perform(patch("/api/v1/dishes/1")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{}"))
                                .andExpect(status().isBadRequest())
                                .andExpect(jsonPath("$.success").value(false))
                                .andExpect(jsonPath("$.message").value("Nenhum campo informado para a atualização"))
                                .andExpect(jsonPath("$.data").isEmpty());
        }

        @Test
        @DisplayName("Deve retonar mensagem de sucesso ao deletar prato")
        public void testDeleteDish() throws Exception {

                dishService.salvarPrato(new DishRequestDTO("Prato", "Descrição detalhada do prato", 10.00));

                mockMvc.perform(delete("/api/v1/dishes/1"))
                                .andExpect(status().isNoContent());
        }

        @Test
        @DisplayName("Deve retonar mensagem de erro ao deletar prato")
        public void testDeleteDishException() throws Exception {

                // métodos com retorno void
                doThrow(new ResourceNotFoundException("Prato não encontrado!")).when(dishService).deletarPrato(1L);

                mockMvc.perform(delete("/api/v1/dishes/1"))
                                .andExpect(status().isNotFound())
                                .andExpect(jsonPath("$.success").value(false))
                                .andExpect(jsonPath("$.message").value("Prato não encontrado!"))
                                .andExpect(jsonPath("$.data").isEmpty());
        }

}
