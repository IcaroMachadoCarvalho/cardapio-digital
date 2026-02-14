package com.example.cardapiodigital.infrastructure.repository;

import com.example.cardapiodigital.infrastructure.entity.Dish;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.List;

public interface DishRepository extends JpaRepository<Dish, Long> {
    List<Dish> findByStatus(String status);

    @Query("SELECT d FROM Dish d WHERE d.preco >= :preco")
    List<Dish> findByPrecoGreaterOrEqual(BigDecimal preco);

    @Query("SELECT d FROM Dish d WHERE d.status = :status AND d.preco = :preco")
    List<Dish> findByStatusAndPrecoGreaterOrEqual(String status, BigDecimal preco);

}
