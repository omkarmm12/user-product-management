package com.omkar.user_product_management.Service;

import com.omkar.user_product_management.Exceptions.NotFoundException;
import com.omkar.user_product_management.Model.Users;
import com.omkar.user_product_management.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<Users> exist = userRepository.findById(email);
        if(exist.isEmpty()){
            throw new NotFoundException("User with "+email+" ot registered yet");
        }
        return new CustomUserDetails(exist.get());
    }
}

