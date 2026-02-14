package com.example.cardapiodigital.infrastructure.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter // gera getters
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "prato") // nome tabela
@Entity
public class Dish {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO) // Auto incrementar
    private Long id;

    @Column(name = "nome", unique = true) // Nome que vai ficar na tabela
    private String nome;

    @Column(name = "descrição")
    private String descricao;

    @Column(name = "preço")
    private BigDecimal preco;

    @Column(name = "data criação")
    private LocalDateTime dataCriacao;

    @Column(name = "data atualização")
    private LocalDateTime dataAtualizacao;

    @Column(name = "status")
    private String status;

    // Roda antes de salvar pela primeira vez
    @PrePersist
    protected void onCreate() {
        dataCriacao = LocalDateTime.now();
        dataAtualizacao = LocalDateTime.now();
    }

    // Roda toda vez que o registro sofrer um Update invés de ter ir na service e ajustar
    @PreUpdate
    protected void onUpdate() {
        dataAtualizacao = LocalDateTime.now();
    }
}
