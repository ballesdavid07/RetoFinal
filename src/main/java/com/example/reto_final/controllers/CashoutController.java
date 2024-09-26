package com.example.reto_final.controllers;

import com.example.reto_final.domain.Cashout;
import com.example.reto_final.exceptions.BalanceException;
import com.example.reto_final.exceptions.Error400Exception;
import com.example.reto_final.exceptions.PaymentException;
import com.example.reto_final.services.interfaces.ICashoutService;
import com.example.reto_final.services.interfaces.IRestClient;
import com.example.reto_final.services.interfaces.IUserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

import java.time.Duration;

@RestController
@RequestMapping("/cashouts")
public class CashoutController {

    @Autowired
    private IUserService userService;

    @Autowired
    private ICashoutService cashoutService;

    @Autowired
    private IRestClient restClient;

    @PostMapping
    public Mono<Cashout> createCashout(@Valid @RequestBody Cashout cashout){
        return this.userService.getUserById(cashout.getUserId())
                .filter(user -> user.getBalance() >= cashout.getAmount())
                .switchIfEmpty(Mono.error(new BalanceException("Balance insuficiente.")))
                .flatMap(user -> getPaymentStatus(cashout))
                .filter("Approved"::equals)
                .switchIfEmpty(Mono.error(new PaymentException("Payment failed.")))
                .flatMap(p -> this.userService.updateBalance(cashout.getUserId(), -cashout.getAmount()))
                .flatMap(user -> this.cashoutService.createCashout(cashout));
    }

    private Mono<String> getPaymentStatus(Cashout cashout) {
        return restClient.validatePayment(cashout)
                .timeout(Duration.ofSeconds(3))
                .retryWhen(
                        Retry.backoff(3, Duration.ofSeconds(2))
                                .filter(error -> !(error instanceof Error400Exception))
                ).doOnError(System.out::println);
    }

    @GetMapping("/user/{userId}")
    public Flux<Cashout> getByUserId(@PathVariable Long userId){
        return this.restClient.getHistoryByUserId(userId);
    }
}
