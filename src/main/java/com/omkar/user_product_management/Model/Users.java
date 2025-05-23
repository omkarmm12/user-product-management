package com.omkar.user_product_management.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Users {
    @Id
    @Email
    private String email;
    @NotBlank
    private String username;
    @NotBlank
    @Column(nullable = false)
    private String password;
    @Enumerated(EnumType.STRING)
    private Role role;

}
