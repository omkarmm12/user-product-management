package com.omkar.user_product_management.Service;

import com.omkar.user_product_management.Exceptions.AlreadyExistsException;
import com.omkar.user_product_management.Exceptions.NotFoundException;
import com.omkar.user_product_management.JwtUtils.JwtUtils;
import com.omkar.user_product_management.Model.BearerToken;
import com.omkar.user_product_management.Model.LoginRequest;
import com.omkar.user_product_management.Model.Role;
import com.omkar.user_product_management.Model.Users;
import com.omkar.user_product_management.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private AuthenticationManager authManager;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;


    public Users getById(String email) {
        Optional<Users> user = userRepository.findById(email);
        if(user.isEmpty()){
            throw new NotFoundException("User with email : "+email+" not exist");
        }
        return user.get();
    }

    public String register(Users user) {
        if(user.getRole() == null || user.getRole().toString().equalsIgnoreCase("user")){
            user.setRole(Role.ROLE_USER);
        }else if(user.getRole().toString().equalsIgnoreCase("admin")){
            user.setRole(Role.ROLE_ADMIN);
        }
        try {
            Optional<Users> exist = userRepository.findById(user.getEmail());
            if(exist.isPresent()){
                throw new AlreadyExistsException("Email already registered. Please use a different email");
            }
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userRepository.save(user);
            return "Successfully Registered";
        }catch (Exception e){
            return e.getMessage();
        }
    }

    public BearerToken verify(LoginRequest userLogin) {
        Authentication authentication = null;
        Optional<Users> existingUser = userRepository.findById(userLogin.getEmail());
        if(existingUser.isEmpty()){
            throw new NotFoundException("Invalid email id");
        }
        try {
            authentication = authManager
                    .authenticate(new UsernamePasswordAuthenticationToken(userLogin.getEmail(), userLogin.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            String token = jwtUtils.generateToken(userDetails);
            return new BearerToken(token);
        }catch (Exception e){
            throw new NotFoundException("Password correct kottu ra");
        }
    }

    public List<Users> get() {
        return userRepository.findAll();
    }

    public void delete(String email) {
        try {
            userRepository.deleteById(email);
        }catch (Exception e) {
            throw new NotFoundException("Email : " + email+" does not exist");
        }
    }

}
