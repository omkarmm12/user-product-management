package com.omkar.user_product_management.Controller;

import com.omkar.user_product_management.Model.Product;
import com.omkar.user_product_management.Service.ProductService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@CrossOrigin(origins = "https://omkarp7hy1rjscpfoqqb.drops.nxtwave.tech")
@RestController
@RequestMapping("/product")
@Validated
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/add")
    public ResponseEntity<Product> add(@Valid @RequestBody Product product) {
        return ResponseEntity.status(HttpStatus.CREATED).body( productService.Add(product));
    }

    @PostMapping("/addall")
    public ResponseEntity<?> addAll(@RequestBody Set<Product> products) {
        return ResponseEntity.status(HttpStatus.CREATED).body(productService.AddAll(products));
    }

    @GetMapping("/getall")
    public List<Product> getAll() {
        return productService.getAll();
    }

    @GetMapping("/getbyid/{id}")
    public ResponseEntity<Product> getById(@PathVariable("id") int id){
       return ResponseEntity.ok(productService.getById(id));
    }

    @GetMapping()
    public ResponseEntity<List<Product>> get(
            @RequestParam Optional<String> sort_by,
            @RequestParam Optional<String> category,
            @RequestParam Optional<Double> price,
            @RequestParam Optional<Float> rating,
            @RequestParam Optional<String> title_search
    ){
        String sortValue = sort_by.orElse("LOW_HIGH");
        String categoryValue = category.orElse("");
        Double priceValue = price.orElse(20.0d);
        Float ratingValue = rating.orElse(0.0f);
        String titleValue = title_search.orElse("");
        List<Product>products= productService.get(sortValue, priceValue , ratingValue,categoryValue, titleValue);
        return ResponseEntity.ok(products);
    }

    @GetMapping("/getbytitleignorecase/{title}")
    public ResponseEntity<List<Product> > getByTitleIgnoreCase(@PathVariable("title") String title) {
        List<Product> products = productService.getByNameIgnoreCase(title);
        return ResponseEntity.ok(products);

    }

    @DeleteMapping("/deletebyid/{id}")
    public ResponseEntity deleteById(@PathVariable("id") int id){
        productService.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @DeleteMapping("/deleteall")
    public ResponseEntity<?>deleteAll(){
        boolean delete=productService.deleteAll();
        if (delete) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("All deleted");
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
