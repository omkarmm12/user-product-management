package com.omkar.user_product_management.Service;

import com.omkar.user_product_management.Exceptions.UserAlreadyExistsException;
import com.omkar.user_product_management.Exceptions.UserNotFoundException;
import com.omkar.user_product_management.Model.Product;
import com.omkar.user_product_management.Repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }


    public Product Add(Product product) {
        boolean Existing=productRepository.existsById(product.getName());
        if (Existing){
            throw  new UserAlreadyExistsException("product with name : "+product.getName()+ " already exist.");
        }else{
            return productRepository.save(product);
        }

    }

    public List<Product> AddAll(Set<Product> products) {
//
        List<Product>productList1= new ArrayList<>(products);
        List<Product>productList2=new ArrayList<>();
//        productRepository.saveAll(productList1);
        productList1.forEach((product)->{productRepository.save(product);
            productList2.add(product); });
        return productList2;
    }
    public List<Product> getAll() {
        return productRepository.findAll();
    }

    public Optional<Product> getByNameIgnoreCase(String name) {
        Optional<Product> product= productRepository.findByNameIgnoreCase(name);
        if (product.isPresent()){
            return Optional.of(product.get());
        }
        return Optional.empty();
    }

    public List<Product> getByPrice(double price) {
        return productRepository.findByPrice(price);
    }
    public List<Product> getByRatingGraterThan(float rating){
        return productRepository.findByRatingGreaterThan(rating);
    }

    public boolean deleteAll() {
        List<Product> products = productRepository.findAll();
        if (products.isEmpty()) {
            return false;
        } else {
            productRepository.deleteAll();
            return true;
        }
    }


    public Product updateByidName(String name, Product product) {

        Optional<Product> ExistingProduct = productRepository.findById(name);

        if (ExistingProduct.isPresent()) {
            Product Exist = ExistingProduct.get();

            if (product.getPrice() != 0.0d) {
                Exist.setPrice(product.getPrice());
            }
            if (product.getRating() != 0.0f) {
                Exist.setRating(product.getRating());
            }
            productRepository.save(Exist);
            return Exist;
        } else {
            throw  new UserNotFoundException("product not found with name : " +name);

        }
    }

    public List<Product> get(Double price, Float rating, String name) {
        List<Product> pro = productRepository.findByPriceGreaterThanAndRatingGreaterThanAndNameContainingIgnoreCase(price,rating,name);
        System.out.print(pro);
        return  pro;
    }

    public List<Product> findByNameContaining(String name){
        return productRepository.findByNameContaining(name);
    }


}

