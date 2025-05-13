package com.omkar.user_product_management.Exceptions;

public class NotFoundException  extends RuntimeException{

    public NotFoundException(String message){
        super(message);
    }
}
