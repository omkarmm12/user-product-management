package com.omkar.user_product_management.Repository;

import com.omkar.user_product_management.Model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product,String> {
    Optional<Product> findByNameIgnoreCase(String name);
    List<Product> findByPrice(double price);
    List<Product> findByRatingGreaterThan(float rating);
    List<Product> findByPriceGreaterThanAndRatingGreaterThanAndNameContainingIgnoreCase(Double price, Float rating, String name);
    List<Product> findByNameContaining(String name);
}