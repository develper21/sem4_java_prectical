package com.example.crud;

import com.example.crud.entity.Product;
import com.example.crud.repository.ProductRepository;
import com.example.crud.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Product Service Unit Tests
 */
@ExtendWith(MockitoExtension.class)
class ProductServiceTest {
    
    @Mock
    private ProductRepository productRepository;
    
    @InjectMocks
    private ProductService productService;
    
    private Product testProduct;
    
    @BeforeEach
    void setUp() {
        testProduct = new Product("Laptop", "High-performance laptop", 999.99, 10);
        testProduct.setId(1L);
    }
    
    @Test
    void testCreateProduct() {
        when(productRepository.existsByName("Laptop")).thenReturn(false);
        when(productRepository.save(any(Product.class))).thenReturn(testProduct);
        
        Product result = productService.createProduct(testProduct);
        
        assertNotNull(result);
        assertEquals("Laptop", result.getName());
        verify(productRepository, times(1)).save(testProduct);
    }
    
    @Test
    void testCreateProduct_DuplicateName() {
        when(productRepository.existsByName("Laptop")).thenReturn(true);
        
        assertThrows(IllegalArgumentException.class, () -> {
            productService.createProduct(testProduct);
        });
    }
    
    @Test
    void testGetAllProducts() {
        when(productRepository.findAll()).thenReturn(Arrays.asList(testProduct));
        
        var products = productService.getAllProducts();
        
        assertEquals(1, products.size());
        verify(productRepository, times(1)).findAll();
    }
    
    @Test
    void testGetProductById() {
        when(productRepository.findById(1L)).thenReturn(Optional.of(testProduct));
        
        Optional<Product> result = productService.getProductById(1L);
        
        assertTrue(result.isPresent());
        assertEquals("Laptop", result.get().getName());
    }
    
    @Test
    void testUpdateProduct() {
        when(productRepository.findById(1L)).thenReturn(Optional.of(testProduct));
        when(productRepository.save(any(Product.class))).thenReturn(testProduct);
        
        Product updatedProduct = new Product("Updated Laptop", "Updated desc", 899.99, 5);
        Product result = productService.updateProduct(1L, updatedProduct);
        
        assertEquals("Updated Laptop", result.getName());
        assertEquals(899.99, result.getPrice());
    }
    
    @Test
    void testDeleteProduct() {
        when(productRepository.findById(1L)).thenReturn(Optional.of(testProduct));
        doNothing().when(productRepository).delete(testProduct);
        
        assertDoesNotThrow(() -> productService.deleteProduct(1L));
        verify(productRepository, times(1)).delete(testProduct);
    }
    
    @Test
    void testSearchProducts() {
        when(productRepository.findByNameContainingIgnoreCase("lap"))
            .thenReturn(Arrays.asList(testProduct));
        
        var results = productService.searchProducts("lap");
        
        assertEquals(1, results.size());
    }
    
    @Test
    void testGetProductCount() {
        when(productRepository.count()).thenReturn(5L);
        
        long count = productService.getProductCount();
        
        assertEquals(5L, count);
    }
}
