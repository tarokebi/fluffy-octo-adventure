package com.example.demo;

import java.util.Map;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
public class FoaBackendApp {

    public static void main(String[] args) {
        SpringApplication.run(FoaBackendApp.class, args);
    }
}

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:3000") // CORS設定
class RootController {

    @GetMapping
    public ResponseEntity<Map<String, String>> root() {
        return ResponseEntity.ok(Map.of("status", "ok"));
    }
}
