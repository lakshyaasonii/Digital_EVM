package com.example.digital_evm.Exceptions;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AgeNotValidException.class)
    public ResponseEntity<?> AgeExceptionHandler(AgeNotValidException e){
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DuplicateKeyException.class)
    public ResponseEntity<?> DuplicateKeyExceptionHandler(DuplicateKeyException e){
        return new ResponseEntity<>(e.getMessage(),HttpStatus.CONFLICT);
    }

    @ExceptionHandler(InvalidRequestException.class)
    public ResponseEntity<?> InvalidRequestHandler(InvalidRequestException e){
        return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
    }

    //Generic Exception Handler
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> ExceptionHandler(Exception e){
        return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
    }



}
