package com.example.cardapiodigital.services;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.cardapiodigital.dto.request.DishRequestDTO;
import com.example.cardapiodigital.dto.request.DishUpdateDTO;
import com.example.cardapiodigital.infrastructure.entity.Dish;
import com.example.cardapiodigital.infrastructure.exception.ResourceNotFoundException;
import com.example.cardapiodigital.infrastructure.repository.DishRepository;

// para usar o Mockito junit5
// Inicializa os @Mock
// Injeta os mocks nos @InjectMocks
// Garante ciclo de vida correto por teste
@ExtendWith(MockitoExtension.class)
public class DishServiceTest {

    @Mock
    private DishRepository dishRepository;

    @InjectMocks // injeta os mocks automaticamente da service
    private DishService dishService;

    @Test
    @DisplayName("Deve salvar prato com sucesso")
    public void saveDish() {
        DishRequestDTO dish = new DishRequestDTO("Prato", "Descrição detalhada do prato", 10.00);
        Dish dishSaved = new Dish();
        dishSaved.setId(1L);
        dishSaved.setNome("Prato");

        when(dishRepository.save(any(Dish.class))).thenReturn(dishSaved);
        Dish result = dishService.salvarPrato(dish);

        assertAll(
                () -> assertNotNull(result),
                () -> assertEquals("Prato", result.getNome()),
                () -> assertEquals(1L, result.getId()));

        verify(dishRepository, times(1)).save(any(Dish.class));
    }

    @Test
    @DisplayName("Deve listar todos os pratos")
    public void findAllDishesWhenBothNull() {
        Dish dish = new Dish();
        dish.setId(1L);
        dish.setNome("Prato");
        dish.setDescricao("Descrição detalhada do prato");
        dish.setPreco(BigDecimal.valueOf(10));
        dish.setStatus("DISPONÍVEL");
        List<Dish> mockList = List.of(dish);
        when(dishRepository.findAll()).thenReturn(mockList);

        List<Dish> result = dishService.listarPratos(null, null);
        assertAll(
                () -> assertNotNull(result),
                () -> assertEquals(1, result.size()),
                () -> assertEquals("Prato", result.get(0).getNome()));

        verify(dishRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Deve listar pratos por status")
    public void findAllDishesByStatus() {
        Dish dish = new Dish();
        dish.setId(1L);
        dish.setNome("Prato");
        dish.setDescricao("Descrição detalhada do prato");
        dish.setPreco(BigDecimal.valueOf(10));
        dish.setStatus("DISPONÍVEL");
        List<Dish> mockList = List.of(dish);
        when(dishRepository.findByStatus("DISPONÍVEL")).thenReturn(mockList);

        List<Dish> result = dishService.listarPratos("DISPONÍVEL", null);
        assertAll(
                () -> assertNotNull(result),
                () -> assertEquals(1, result.size()),
                () -> assertEquals("Prato", result.get(0).getNome()));

        verify(dishRepository, times(1)).findByStatus("DISPONÍVEL");
    }

    @Test
    @DisplayName("Deve listar pratos por preço igual ou maior")
    public void findAllDishesByPreco() {
        Dish dish = new Dish();
        dish.setId(1L);
        dish.setNome("Prato");
        dish.setDescricao("Descrição detalhada do prato");
        dish.setPreco(BigDecimal.valueOf(10));
        dish.setStatus("DISPONÍVEL");
        List<Dish> mockList = List.of(dish);
        when(dishRepository.findByPrecoGreaterOrEqual(BigDecimal.valueOf(10.0))).thenReturn(mockList);

        List<Dish> result = dishService.listarPratos(null, 10.0);
        assertAll(
                () -> assertNotNull(result),
                () -> assertEquals(1, result.size()),
                () -> assertEquals("Prato", result.get(0).getNome()));
    }

    @Test
    @DisplayName("Deve listar pratos por preço e status")
    public void findAllDishesByPrecoAndStatus() {
        Dish dish = new Dish();
        dish.setId(1L);
        dish.setNome("Prato");
        dish.setDescricao("Descrição detalhada do prato");
        dish.setPreco(BigDecimal.valueOf(10));
        dish.setStatus("DISPONÍVEL");
        List<Dish> mockList = List.of(dish);
        when(dishRepository.findByStatusAndPrecoGreaterOrEqual("DISPONÍVEL", BigDecimal.valueOf(10.0)))
                .thenReturn(mockList);

        List<Dish> result = dishService.listarPratos("DISPONÍVEL", 10.0);
        assertAll(
                () -> assertNotNull(result),
                () -> assertEquals(1, result.size()),
                () -> assertEquals("Prato", result.get(0).getNome()));
    }

    @Test
    @DisplayName("Deve listar prato por Id")
    public void findById() {
        Dish savedDish = new Dish();
        savedDish.setId(1L);
        savedDish.setNome("Prato");
        when(dishRepository.findById(1L)).thenReturn(Optional.of(savedDish));

        Dish result = dishService.listarPrato(1L);
        assertAll(
                () -> assertNotNull(result),
                () -> assertEquals(savedDish.getId(), result.getId()));

        verify(dishRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("Deve lançar exception ao listar prato por Id")
    public void findByIdException() {
        when(dishRepository.findById(1L)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            dishService.listarPrato(1L);
        });

        assertEquals("Prato não encontrado!", exception.getMessage());
        verify(dishRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("Deve atualizar prato")
    public void updateDish() {
        DishUpdateDTO dish = new DishUpdateDTO("Prato", "Descrição detalhada do prato", 10.00, "DISPONÍVEL");

        Dish savedDish = new Dish();
        savedDish.setId(1L);
        savedDish.setNome("Prato");
        savedDish.setDescricao("Descrição detalhada do prato");
        savedDish.setPreco(BigDecimal.valueOf(10));
        savedDish.setStatus("DISPONÍVEL");

        when(dishRepository.findById(1L)).thenReturn(Optional.of(savedDish));
        when(dishRepository.save(savedDish)).thenReturn(savedDish);

        Dish result = dishService.atualizarPrato(1L, dish);
        assertAll(
                () -> assertNotNull(result),
                () -> assertEquals(savedDish.getId(), result.getId()),
                () -> assertEquals(dish.name(), result.getNome()),
                () -> assertEquals(dish.description(), result.getDescricao()),
                () -> assertEquals(BigDecimal.valueOf(dish.price()), result.getPreco()),
                () -> assertEquals(dish.status().toUpperCase(), result.getStatus()));

        verify(dishRepository, times(1)).findById(1L);
        verify(dishRepository, times(1)).save(savedDish);
    }

    @Test
    @DisplayName("Deve lançar exception ao atualizar prato")
    public void updateDishException() {
        DishUpdateDTO dish = new DishUpdateDTO("Prato", "Descrição detalhada do prato", 10.00, "DISPONÍVEL");

        when(dishRepository.findById(1L)).thenReturn(Optional.empty());
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            dishService.atualizarPrato(1L, dish);
        });
        assertEquals("Prato não encontrado!", exception.getMessage());
        verify(dishRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("Deve deletar prato com sucesso")
    public void deleteDish() {
        Dish dish = new Dish();
        dish.setId(1L);
        dish.setNome("Prato");

        when(dishRepository.findById(1L)).thenReturn(Optional.of(dish));
        dishService.deletarPrato(1L);
        verify(dishRepository, times(1)).deleteById(1L);
    }

    @Test
    @DisplayName("Deve lançar exception ao deletar prato")
    public void deleteDishException() {
        when(dishRepository.findById(1L)).thenReturn(Optional.empty());
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            dishService.deletarPrato(1L);
        });
        assertEquals("Prato nao encontrado!", exception.getMessage());
        verify(dishRepository, times(1)).findById(1L);
    }

}
