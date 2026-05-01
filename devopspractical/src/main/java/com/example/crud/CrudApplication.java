package com.example.crud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Spring Boot CRUD Application Entry Point
 * 
 * This is a minimal CRUD application demonstrating:
 * - Product entity management
 * - REST API endpoints
 * - JPA repository pattern
 * - Service layer architecture
 * 
 * @author Developer
 * @version 1.0.0
 */
@SpringBootApplication
public class CrudApplication {
    
    public static void main(String[] args) {
        SpringApplication.run(CrudApplication.class, args);
    }
}
