package com.omkar.user_product_management.Model;

public class BearerToken {
    private String jwt_token;

    public BearerToken(String jwt_token) {
        this.jwt_token = jwt_token;
    }

    public String getJwt_token() {
        return jwt_token;
    }

    public void setJwt_token(String jwt_token) {
        this.jwt_token = jwt_token;
    }
}
