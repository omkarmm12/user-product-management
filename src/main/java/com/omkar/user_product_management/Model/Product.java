package com.omkar.user_product_management.Model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;

import java.time.LocalDateTime;

@Entity
public class Product {

    private String imgUrl;

    @Id
    @Column(unique = true)
    private String name;

    private double price;
    private float rating;

    @Column(updatable = false)
    private LocalDateTime time_stamp;

    // Default Constructor
    public Product() {
    }

    // Parameterized Constructor
    public Product(String imgUrl, String name, double price, float rating, LocalDateTime time_stamp) {
        this.imgUrl = imgUrl;
        this.name = name;
        this.price = price;
        this.rating = rating;
        this.time_stamp = time_stamp;
    }

    // Getters and Setters
    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public LocalDateTime getTime_stamp() {
        return time_stamp;
    }

    public void setTime_stamp(LocalDateTime time_stamp) {
        this.time_stamp = time_stamp;
    }

    // PrePersist Method
    @PrePersist
    protected void onCreate() {
        time_stamp = LocalDateTime.now();
    }
}
