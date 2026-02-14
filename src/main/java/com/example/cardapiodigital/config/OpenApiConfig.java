package com.example.cardapiodigital.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

/*
    🔹 @Configuration

    @Configuration é uma anotação do Spring que indica que a classe é uma classe de configuração.

    Ou seja, ela serve para definir beans manualmente dentro do contexto da aplicação.

    ✅ O que significa na prática?

    Quando o Spring inicia a aplicação:

    Ele encontra a classe marcada com @Configuration

    Lê os métodos anotados com @Bean

    E registra os objetos retornados como beans gerenciados pelo Spring

    🔹 @Bean

    @Bean indica que o método vai criar e fornecer um objeto que será gerenciado pelo Spring.

    ✅ O que acontece?

    Quando o Spring sobe a aplicação:

    Ele executa o método customOpenAPI()

    Pega o objeto retornado (new OpenAPI())

    Registra esse objeto no Container IoC (Inversion of Control)

    Permite que ele seja injetado em outras partes da aplicação

    🔹 O que é um Bean?

    Um Bean é simplesmente:

    Um objeto que o Spring cria, controla e gerencia.

    Ele fica dentro do ApplicationContext (container do Spring).
*/

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info().title("Cardapio Digital").version("1.0.0")
                        .description("API para gerenciamento de pratos"));
    }

}