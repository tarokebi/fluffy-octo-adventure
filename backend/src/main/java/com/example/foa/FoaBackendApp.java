package com.example.foa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class FoaBackendApp {
    public static void main(String[] args) {
        SpringApplication.run(FoaBackendApp.class, args);
    }
}
