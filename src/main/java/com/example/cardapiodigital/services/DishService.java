package com.example.cardapiodigital.services;

import com.example.cardapiodigital.dto.request.DishRequestDTO;
import com.example.cardapiodigital.dto.request.DishUpdateDTO;
import com.example.cardapiodigital.infrastructure.entity.Dish;
import com.example.cardapiodigital.infrastructure.exception.BadRequestException;
import com.example.cardapiodigital.infrastructure.exception.ResourceNotFoundException;
import com.example.cardapiodigital.infrastructure.repository.DishRepository;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class DishService {
    private DishRepository dishRepository;

    public DishService(DishRepository dishRepository) {
        this.dishRepository = dishRepository;
    }

    @CacheEvict(value = "pratos", allEntries = true)
    public Dish salvarPrato(DishRequestDTO dto) {
        Dish dish = new Dish();
        dish.setNome(dto.name()); // esse getter é do record
        dish.setDescricao(dto.description());
        dish.setPreco(BigDecimal.valueOf(dto.price())); // Convertendo Double para BigDecimal
        dish.setStatus("DISPONÍVEL");

        return dishRepository.save(dish);
    }

    // @Cacheable(value = "dishes", key = "#status + '-' + #price") esse não tem
    // tratamento para null
    @Cacheable(value = "pratos", key = "(#status != null ? #status : 'TODOS') + '-' + (#price != null ? #price : 0)")

    public List<Dish> listarPratos(String status, Double price) {
        if (status == null && price == null) {
            return dishRepository.findAll();
        }
        if (status != null && price == null) {
            return dishRepository.findByStatus(status);
        }
        if (status == null && price != null) {
            return dishRepository.findByPrecoGreaterOrEqual(BigDecimal.valueOf(price));
        }
        return dishRepository.findByStatusAndPrecoGreaterOrEqual(status, BigDecimal.valueOf(price));
    }

    @Cacheable(value = "dish", key = "#id")
    public Dish listarPrato(Long id) {
        return dishRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Prato não encontrado!"));
    }

    // Limpa todos os caches
    @CacheEvict(value = "dishes", allEntries = true)
    public Dish atualizarPrato(Long id, DishUpdateDTO dto) {
        Dish dish = dishRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Prato não encontrado!"));

        if (dto.isEmpty()) {
            throw (new BadRequestException("Nenhum campo informado para a atualização"));
        }
        if (dto.name() != null) {
            dish.setNome(dto.name());
        }
        if (dto.description() != null) {
            dish.setDescricao(dto.description());
        }
        if (dto.price() != null) {
            dish.setPreco(BigDecimal.valueOf(dto.price()));
        }
        if (dto.status() != null) {
            dish.setStatus(dto.status().toUpperCase());
        }

        return dishRepository.save(dish);
        // Com o save vai ativar o pre update do entity
    }

    @CacheEvict(value = "dishes", allEntries = true)
    public void deletarPrato(Long id) {
        dishRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Prato nao encontrado!"));
        dishRepository.deleteById(id);
    }
}
