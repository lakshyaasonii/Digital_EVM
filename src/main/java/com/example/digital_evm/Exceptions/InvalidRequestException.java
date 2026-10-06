package com.example.digital_evm.Exceptions;

public class InvalidRequestException extends RuntimeException{
    public InvalidRequestException(String msg){
        super(msg);
    }
}
