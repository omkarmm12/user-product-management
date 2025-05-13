package com.omkar.user_product_management.Exceptions;

public class AlreadyExistsException extends RuntimeException{

    public AlreadyExistsException(String message){
        super(message);
    }

}
