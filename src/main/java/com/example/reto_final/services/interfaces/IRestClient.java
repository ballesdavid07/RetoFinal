package com.example.reto_final.services.interfaces;

import com.example.reto_final.domain.Cashout;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.PostExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IRestClient {
    @PostExchange("/payments")
    Mono<String> validatePayment(@RequestBody Cashout cashout);

    @GetExchange("/transaction-history/user/{userId}")
    Flux<Cashout> getHistoryByUserId(@PathVariable Long userId);
}
