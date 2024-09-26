package com.example.reto_final.controllers;

import com.example.reto_final.exceptions.BalanceException;
import com.example.reto_final.exceptions.PaymentException;
import com.example.reto_final.exceptions.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.support.WebExchangeBindException;
import reactor.core.publisher.Mono;

@RestControllerAdvice
public class GlobalHandleError {

    @ExceptionHandler({UserNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Mono<ResponseEntity<String>> handleNoFoundException(UserNotFoundException exception){
        return Mono.just(ResponseEntity.notFound().build());
    }

    @ExceptionHandler({BalanceException.class, PaymentException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Mono<ResponseEntity<String>> handleBalanceExceptions(RuntimeException ex) {
        return Mono.just(ResponseEntity.status((HttpStatus.BAD_REQUEST)).body(ex.getMessage()));
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.BAD_GATEWAY)
    public Mono<ResponseEntity<String>> handleValidationExceptions(RuntimeException ex) {
        return Mono.just(ResponseEntity.status((HttpStatus.BAD_GATEWAY)).body(ex.getMessage()));
    }

    @ExceptionHandler(WebExchangeBindException.class)
    public Mono<ResponseEntity<String>> handleValidationExceptions(WebExchangeBindException ex) {
        String errorMessage = ex.getBindingResult().getAllErrors().get(0).getDefaultMessage();
        return Mono.just(ResponseEntity.badRequest().body(errorMessage));
    }

}