package com.example.cardapiodigital.repository;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.example.cardapiodigital.infrastructure.entity.Dish;
import com.example.cardapiodigital.infrastructure.repository.DishRepository;

@DataJpaTest
public class DishRepositoryTest {

    @Autowired
    private DishRepository dishRepository;

    @Test
    @DisplayName("Deve retornar pratos por status")
    public void testFindByStatus() {
        Dish dish = new Dish();
        dish.setNome("Prato");
        dish.setDescricao("Descrição detalhada do prato");
        dish.setPreco(BigDecimal.valueOf(10));
        dish.setStatus("DISPONÍVEL");

        dishRepository.save(dish);
        List<Dish> dishes = dishRepository.findByStatus("DISPONÍVEL");

        assertAll(
                () -> assertFalse(dishes.isEmpty()),
                () -> assertEquals(1, dishes.size()),
                () -> assertEquals(dish, dishes.get(0)));
        // assertFalse(dishes.isEmpty());
        // assertEquals(1, dishes.size());
        // assertEquals("Prato", dishes.get(0).getNome());
    }

    @Test
    @DisplayName("Deve retonar lista de tamanho zero")
    public void testFindByStatusEmpty() {
        List<Dish> dishes = dishRepository.findByStatus("DISPONÍVEL");
        assertTrue(dishes.size() == 0);
    }

    @Test
    @DisplayName("Deve retonar pratos com preços iguais ou superiores")
    public void testFindByPrecoGreaterOrEqual() {
        Dish dish = new Dish();
        dish.setNome("Prato");
        dish.setDescricao("Descrição detalhada do prato");
        dish.setPreco(BigDecimal.valueOf(10));
        dish.setStatus("DISPONÍVEL");

        dishRepository.save(dish);
        Dish dish1 = new Dish();
        dish1.setNome("Prato 2");
        dish1.setDescricao("Descrição detalhada do prato");
        dish1.setPreco(BigDecimal.valueOf(5));
        dish1.setStatus("DISPONÍVEL");

        dishRepository.save(dish1);
        List<Dish> dishes = dishRepository.findByPrecoGreaterOrEqual(BigDecimal.valueOf(10));

        assertAll(
                () -> assertFalse(dishes.isEmpty()),
                () -> assertEquals(1, dishes.size()),
                () -> assertEquals(dish, dishes.get(0)));
    }

    @Test
    @DisplayName("Deve retonar lista vazia")
    public void testFindByPrecoGreaterOrEqualEmpty() {
        List<Dish> dishes = dishRepository.findByPrecoGreaterOrEqual(BigDecimal.valueOf(10));
        assertTrue(dishes.size() == 0);
    }

    @Test
    @DisplayName("Deve retonar pratos com status igual e preços iguais ou superiores")
    public void testFindByStatusAndPrecoGreaterOrEqual() {
        Dish dish = new Dish();
        dish.setNome("Prato");
        dish.setDescricao("Descrição detalhada do prato");
        dish.setPreco(BigDecimal.valueOf(10));
        dish.setStatus("INDISPONÍVEL");

        dishRepository.save(dish);
        Dish dish1 = new Dish();
        dish1.setNome("Prato 2");
        dish1.setDescricao("Descrição detalhada do prato");
        dish1.setPreco(BigDecimal.valueOf(5));
        dish1.setStatus("DISPONÍVEL");

        dishRepository.save(dish1);
        List<Dish> dishes = dishRepository.findByStatusAndPrecoGreaterOrEqual("DISPONÍVEL", BigDecimal.valueOf(5));

        assertAll(
                () -> assertFalse(dishes.isEmpty()),
                () -> assertEquals(1, dishes.size()),
                () -> assertEquals(dish1, dishes.get(0)));
    }

    @Test
    @DisplayName("Deve retonar lista vazia")
    public void testFindByStatusAndPrecoGreaterOrEqualEmpty() {
        List<Dish> dishes = dishRepository.findByStatusAndPrecoGreaterOrEqual("DISPONÍVEL", BigDecimal.valueOf(5));
        assertTrue(dishes.size() == 0);
    }

    @Test
    @DisplayName("Deve retonar lista vazia")
    public void testFindByStatusAndPrecoGreaterOrEqualEmpty2() {
        List<Dish> dishes = dishRepository.findByStatusAndPrecoGreaterOrEqual(null, BigDecimal.valueOf(5));
        assertTrue(dishes.size() == 0);
    }

}
