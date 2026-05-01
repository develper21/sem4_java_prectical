package com.example.crud.service;

import com.example.crud.entity.Product;
import com.example.crud.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Product Service Layer
 * 
 * Business logic implementation:
 * - CRUD operations
 * - Validation
 * - Business rules
 */
@Service
@Transactional
public class ProductService {
    
    private final ProductRepository productRepository;
    
    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }
    
    /**
     * Create a new product
     */
    public Product createProduct(Product product) {
        // Business rule: Check if product with same name exists
        if (productRepository.existsByName(product.getName())) {
            throw new IllegalArgumentException("Product with name '" + product.getName() + "' already exists");
        }
        return productRepository.save(product);
    }
    
    /**
     * Get all products
     */
    @Transactional(readOnly = true)
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }
    
    /**
     * Get product by ID
     */
    @Transactional(readOnly = true)
    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id);
    }
    
    /**
     * Update existing product
     */
    public Product updateProduct(Long id, Product productDetails) {
        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + id));
        
        // Update fields
        existingProduct.setName(productDetails.getName());
        existingProduct.setDescription(productDetails.getDescription());
        existingProduct.setPrice(productDetails.getPrice());
        existingProduct.setQuantity(productDetails.getQuantity());
        
        return productRepository.save(existingProduct);
    }
    
    /**
     * Delete product by ID
     */
    public void deleteProduct(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + id));
        productRepository.delete(product);
    }
    
    /**
     * Search products by name
     */
    @Transactional(readOnly = true)
    public List<Product> searchProducts(String name) {
        return productRepository.findByNameContainingIgnoreCase(name);
    }
    
    /**
     * Get products with price less than
     */
    @Transactional(readOnly = true)
    public List<Product> getProductsByMaxPrice(Double maxPrice) {
        return productRepository.findByPriceLessThan(maxPrice);
    }
    
    /**
     * Get products in stock (quantity > 0)
     */
    @Transactional(readOnly = true)
    public List<Product> getProductsInStock() {
        return productRepository.findByQuantityGreaterThan(0);
    }
    
    /**
     * Get product count
     */
    @Transactional(readOnly = true)
    public long getProductCount() {
        return productRepository.count();
    }
    
    /**
     * Get products in price range
     */
    @Transactional(readOnly = true)
    public List<Product> getProductsByPriceRange(Double minPrice, Double maxPrice) {
        return productRepository.findByPriceRange(minPrice, maxPrice);
    }
}
