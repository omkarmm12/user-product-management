package com.omkar.user_product_management.Model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class BearerToken {
    private String jwt_token;
}
