package com.example.digital_evm.Exceptions;

public class AgeNotValidException extends RuntimeException{
    public AgeNotValidException (String msg){
        super(msg);
    }
}
