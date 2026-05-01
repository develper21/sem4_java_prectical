package com.example.crud;

import com.example.crud.entity.Product;
import com.example.crud.repository.ProductRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Product Controller Integration Tests
 */
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class ProductControllerIntegrationTest {
    
    @Autowired
    private MockMvc mockMvc;
    
    @Autowired
    private ObjectMapper objectMapper;
    
    @Autowired
    private ProductRepository productRepository;
    
    private Product testProduct;
    
    @BeforeEach
    void setUp() {
        // Clean up database before each test to avoid conflicts
        productRepository.deleteAll();
        
        // Use unique product name with timestamp to avoid duplicate name errors
        testProduct = new Product("Test Product " + System.currentTimeMillis(), "Test Description", 99.99, 5);
    }
    
    @Test
    void testHealthCheck() throws Exception {
        mockMvc.perform(get("/api/products/health"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("UP"));
    }
    
    @Test
    void testCreateAndGetProduct() throws Exception {
        // Create product
        String productJson = objectMapper.writeValueAsString(testProduct);
        
        mockMvc.perform(post("/api/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(productJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value(testProduct.getName()));
        
        // Get all products
        mockMvc.perform(get("/api/products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value(testProduct.getName()));
    }
    
    @Test
    void testGetProductById_NotFound() throws Exception {
        mockMvc.perform(get("/api/products/999"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").exists());
    }
    
    @Test
    void testSearchProducts() throws Exception {
        // First create a product
        String productJson = objectMapper.writeValueAsString(testProduct);
        mockMvc.perform(post("/api/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(productJson))
                .andExpect(status().isCreated());
        
        // Search for it
        mockMvc.perform(get("/api/products/search")
                .param("name", "Test"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value(testProduct.getName()));
    }
    
    @Test
    void testGetProductsCount() throws Exception {
        mockMvc.perform(get("/api/products/count"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.count").exists());
    }
    
    @Test
    void testGetProductsInStock() throws Exception {
        mockMvc.perform(get("/api/products/in-stock"))
                .andExpect(status().isOk());
    }
}
