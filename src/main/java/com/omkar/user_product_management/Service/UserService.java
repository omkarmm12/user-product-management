package com.omkar.user_product_management.Service;

import com.omkar.user_product_management.Exceptions.UserAlreadyExistsException;
import com.omkar.user_product_management.Exceptions.UserNotFoundException;
import com.omkar.user_product_management.Model.BearerToken;
import com.omkar.user_product_management.Model.UserLogin;
import com.omkar.user_product_management.Model.Users;
import com.omkar.user_product_management.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    private AuthenticationManager authManager;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public String register(Users user) {
        try {
            Optional<Users> exist = userRepository.findById(user.getEmail());
            if(exist.isPresent()){
                throw new UserAlreadyExistsException("Email already registered. Please use a different email");
            }
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userRepository.save(user);
            return "Successfully Registered";
        }catch (Exception e){
            return e.getMessage();
        }
    }

    public BearerToken verify(UserLogin userLogin) {
        Optional<Users> existingUser = userRepository.findById(userLogin.getEmail());
        if(existingUser.isEmpty()){
            throw new UserNotFoundException("Invalid email id");
        }
        try {
            Authentication authentication = authManager
                    .authenticate(new UsernamePasswordAuthenticationToken(userLogin.getEmail(), userLogin.getPassword()));

        }catch (Exception e){
            throw new UserNotFoundException("Invalid password");
        }

        return new BearerToken("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VybmFtZSI6InJhaHVsIiwicm9sZSI6IlBSSU1FX1VTRVIiLCJpYXQiOjE2MjMwNjU1MzJ9.D13s5wN3Oh59aa_qtXMo3Ec4wojOx0EZh8Xr5C5sRkU");
    }

    public List<Users> get() {
        return userRepository.findAll();
    }

    public void delete(String email) {
        try {
            userRepository.deleteById(email);
        }catch (Exception e) {
            throw new UserNotFoundException("No user with " + email);
        }
    }
}
