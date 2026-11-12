package com.example.digital_evm.Exceptions;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    //Exception handler method for duplicate adharNumber(username)
    @ExceptionHandler(DuplicateKeyException.class)
    public ResponseEntity<?> DuplicateKeyExceptionHandler(DuplicateKeyException e){
        return new ResponseEntity<>("Voter with this adharNumber is already exist!! please try different valid adharnumber!!",HttpStatus.CONFLICT);
    }

    //Exveption handler for Invalid Request such as UserNotFound etc etc
    @ExceptionHandler(InvalidRequestException.class)
    public ResponseEntity<?> InvalidRequestHandler(InvalidRequestException e){
        return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
    }

    //Exception handler method for POJO attributes VALIDATION
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidationException(MethodArgumentNotValidException e){
        String errorMessage = e.getBindingResult().getFieldError().getDefaultMessage();
        return new ResponseEntity<>(errorMessage,HttpStatus.BAD_REQUEST);
    }

    //Generic Exception Handler
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> ExceptionHandler(Exception e){
        return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
    }



}
