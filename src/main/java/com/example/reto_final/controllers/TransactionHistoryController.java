package com.example.reto_final.controllers;

import com.example.reto_final.domain.Cashout;
import com.example.reto_final.services.interfaces.ICashoutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/transaction-history")
public class TransactionHistoryController {

    @Autowired
    private ICashoutService cashoutService;

    @GetMapping("/user/{userId}")
    public Flux<Cashout> getByUserId(@PathVariable Long userId){
        return this.cashoutService.getCashoutByUserId(userId);
    }
}
