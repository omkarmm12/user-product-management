package com.omkar.user_product_management.Controller;

import com.omkar.user_product_management.Model.BearerToken;
import com.omkar.user_product_management.Model.LoginRequest;
import com.omkar.user_product_management.Model.Users;
import com.omkar.user_product_management.Service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;


    @PostMapping("/register")
    public String register(@RequestBody Users user){
        return userService.register(user);
    }
    @PostMapping("/login")
    public BearerToken login(@RequestBody LoginRequest userLogin){
        return userService.verify(userLogin);
    }

    @GetMapping("/get")
    public List<Users> get(){
        return userService.get();
    }

    @GetMapping("/get/{email}")
    public Users getById(@PathVariable("email") String email){
        return userService.getById(email);
    }

    @DeleteMapping("/delete/{email}")
    public ResponseEntity<?> delete(@PathVariable("email") String email){
        userService.delete(email);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("User Successfully Deleted");
    }

}