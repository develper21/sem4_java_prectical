package com.example.crud.repository;

import com.example.crud.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Product Repository Interface
 * 
 * Extends JpaRepository to provide CRUD operations:
 * - save() - Create/Update
 * - findById() - Read one
 * - findAll() - Read all
 * - deleteById() - Delete
 * 
 * Additional custom queries included.
 */
@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    
    /**
     * Find products by name (case-insensitive, partial match)
     */
    List<Product> findByNameContainingIgnoreCase(String name);
    
    /**
     * Find products with price less than given value
     */
    List<Product> findByPriceLessThan(Double price);
    
    /**
     * Find products with quantity greater than given value
     */
    List<Product> findByQuantityGreaterThan(Integer quantity);
    
    /**
     * Check if product exists by name
     */
    boolean existsByName(String name);
    
    /**
     * Find products with price range
     */
    @Query("SELECT p FROM Product p WHERE p.price BETWEEN ?1 AND ?2")
    List<Product> findByPriceRange(Double minPrice, Double maxPrice);
    
    /**
     * Count total products
     */
    long count();
}
