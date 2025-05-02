package com.omkar.user_product_management.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    @NotBlank
    @Column(nullable = false)
    private String title;
    @NotBlank
    private String brand;
    @NotNull
    @Min(value = 20)
    @Max(value = 1000000)
    private double price;
    @Id
    @Column(nullable = false)
    private int id;
    @Min(value=0)
    @Max(value = 5)
    private float rating;
    private String category;
    private String imageUrl;
    @Column(updatable = false)
    private LocalDateTime time_stamp;
    @PrePersist
    protected void onCreate() {
        time_stamp = LocalDateTime.now();
    }
}
