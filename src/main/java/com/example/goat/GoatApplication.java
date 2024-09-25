package com.example.goat;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class GoatApplication {

    public static void main(String[] args) {
        SpringApplication.run(GoatApplication.class, args);
    }

}
