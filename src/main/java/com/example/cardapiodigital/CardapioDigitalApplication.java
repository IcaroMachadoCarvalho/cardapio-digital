package com.example.cardapiodigital;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class CardapioDigitalApplication {

    public static void main(String[] args) {
        SpringApplication.run(CardapioDigitalApplication.class, args);
    }

}
