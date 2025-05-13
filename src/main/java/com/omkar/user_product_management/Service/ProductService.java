package com.omkar.user_product_management.Service;

import com.omkar.user_product_management.Exceptions.AlreadyExistsException;
import com.omkar.user_product_management.Exceptions.NotFoundException;
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
        boolean Existing=productRepository.existsById(product.getId());
        if (Existing){
            throw  new AlreadyExistsException("Product with Id : "+product.getId()+ " name : "+product.getTitle() + " exist.");
        }else{
            return productRepository.save(product);
        }

    }

    public List<Product> AddAll(Set<Product> products) {
        List<Product>productList1= new ArrayList<>(products);
        List<Product>productList2=new ArrayList<>();
        if(!productList1.isEmpty()) {
            productList1.forEach((product) -> {
                Add(product);
                productList2.add(product);
            });
        }
            return productList2;
    }

    public Product getById(int id) {
        Optional<Product> isExist = productRepository.findById(id);
        if(isExist.isEmpty()){
            throw new NotFoundException("Product with Id : "+id+ " not exist");
        }else{
            return isExist.get();
        }
    }

    public List<Product> getAll() {
        return productRepository.findAll();
    }

    public List<Product> getByNameIgnoreCase(String title) {
        return productRepository.findByTitleIgnoreCase(title);
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


    public Product updateById(int id, Product product) {

        Optional<Product> ExistingProduct = productRepository.findById(id);

        if (ExistingProduct.isPresent()) {
            Product Exist = ExistingProduct.get();

            if (product.getPrice() != 0.0d) {
                Exist.setPrice(product.getPrice());
            }
            if (product.getRating() != 0.0f) {
                Exist.setRating(product.getRating());
            }
            productRepository.save(Exist);
            return productRepository.findById(id).get();
        } else {
            throw  new NotFoundException("product not found with id : " +id);

        }
    }

    public List<Product> get(String sortValue, Double price, Float rating,String category, String title) {
        List<Product> products = productRepository.findByPriceGreaterThanAndRatingGreaterThanAndCategoryContainingIgnoreCaseAndTitleContainingIgnoreCase(price,rating,category, title);
        if(sortValue.equals("LOW_HIGH")){
            products.sort((p1,p2)-> (int) (p1.getPrice() - p2.getPrice()));
            return products;
        }else{
            products.sort((p1,p2)-> (int) (p2.getPrice()-p1.getPrice()));
            return products;
        }
    }


    public void deleteById(int id) {
        Optional<Product> isExist = productRepository.findById(id);
        if(isExist.isEmpty()){
            throw new NotFoundException("Product with Id : "+id+" not found");
        }else{
            productRepository.deleteById(id);
        }
    }

}

