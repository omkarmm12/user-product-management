package com.omkar.user_product_management.Controller;

import com.omkar.user_product_management.Model.Product;
import com.omkar.user_product_management.Service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/product")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/add")
    public ResponseEntity<Product> add(@RequestBody Product product) {
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

    @GetMapping()
    public ResponseEntity<List<Product>> get(
            @RequestParam Optional<Double> price,
            @RequestParam Optional<Float> rating,
            @RequestParam Optional<String> name
    ){
        Double priceValue = price.orElse(0.0d);
        Float ratingValue = rating.orElse(0.0f);
        String nameValue = name.orElse("");
        List<Product>products= productService.get(priceValue , ratingValue, nameValue);
        return ResponseEntity.ok(products);
    }

    @GetMapping("/getbynameignorecase/{name}")
    public ResponseEntity<Optional<Product> > getByNameIgnoreCase(@PathVariable String name) {
        Optional<Product> product = productService.getByNameIgnoreCase(name);
        if (product.isPresent()) {
            return ResponseEntity.ok(product);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/getbyprice/{price}")
    public ResponseEntity<?> getByPrice(@PathVariable double price){
        return ResponseEntity.ok(productService.getByPrice(price));
    }
    @DeleteMapping("/deleteall")
    public ResponseEntity<?>deleteAll(){
        boolean delete=productService.deleteAll();
        if (delete) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("All deleted");
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }


    @PutMapping("/updatebyname/{name}")
    public ResponseEntity<?> updateByIdName(@PathVariable String name,@RequestBody Product product){
        Product updated = productService.updateByidName(name,product);
        if (updated!=null){
            return ResponseEntity.ok(updated);
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("not found with name : " +name );
        }
    }
    @GetMapping("/getbyratinggraterthan/{rating}")
    public ResponseEntity<?>getByRatingGraterThan(@PathVariable float rating){
        List<Product>products= productService.getByRatingGraterThan(rating);
        return ResponseEntity.ok(products);
    }

    @GetMapping("/{name}")
    public ResponseEntity<?> getByNameContaining(@PathVariable("name") String name){
        List<Product> list = productService.findByNameContaining(name);
        System.out.println(list);
        return ResponseEntity.ok(list);
    }

}
