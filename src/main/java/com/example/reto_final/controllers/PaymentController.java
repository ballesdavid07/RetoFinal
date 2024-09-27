package com.example.reto_final.controllers;

import com.example.reto_final.domain.Cashout;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/payments")
public class PaymentController {

    @PostMapping
    Mono<String> validatePayment(@RequestBody Cashout cashout){
        if (cashout.getAmount() >= 50){
            return Mono.just("Approved");
        }
        return Mono.just("Not approved");
    }
}
