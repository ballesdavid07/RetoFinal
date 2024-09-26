package com.example.reto_final.services;

import com.example.reto_final.domain.Cashout;
import com.example.reto_final.domain.repositories.CashoutRepository;
import com.example.reto_final.services.interfaces.ICashoutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class CashoutService implements ICashoutService {

    @Autowired
    private CashoutRepository cashoutRepository;

    @Override
    public Mono<Cashout> createCashout(Cashout cashout) {
        return this.cashoutRepository.save(cashout);
    }

    @Override
    public Flux<Cashout> getCashoutByUserId(Long userId) {
        return this.cashoutRepository.findByUserId(userId);
    }
}
