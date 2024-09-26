package com.example.reto_final.services.interfaces;

import com.example.reto_final.domain.Cashout;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ICashoutService {

    public Mono<Cashout> createCashout(Cashout cashout);

    public Flux<Cashout> getCashoutByUserId(Long userId);
}
