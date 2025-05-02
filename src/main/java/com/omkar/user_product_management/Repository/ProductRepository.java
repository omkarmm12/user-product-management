package com.omkar.user_product_management.Repository;

import com.omkar.user_product_management.Model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product,Integer> {
    List<Product> findByTitleIgnoreCase(String title);
    List<Product> findByPriceGreaterThanAndRatingGreaterThanAndCategoryContainingIgnoreCaseAndTitleContainingIgnoreCase(Double price, Float rating,String category,String title);
    Optional<Product> findByTitle(String title);
}