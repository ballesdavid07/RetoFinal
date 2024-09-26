package com.example.reto_final.services;

import com.example.reto_final.domain.Cashout;
import com.example.reto_final.domain.repositories.CashoutRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;

class CashoutServiceTest {

    @Mock
    CashoutRepository cashoutRepository;

    @InjectMocks
    CashoutService cashoutService;

    @BeforeEach
    public void setup(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createCashout() {
        Cashout cashoutInput = new Cashout(null, 1L, 100.00);
        Cashout cashoutOutput = new Cashout(1L, 1L, 100.00);

        Mockito.when(cashoutRepository.save(any())).thenReturn(Mono.just(cashoutOutput));

        StepVerifier.create(cashoutService.createCashout(cashoutInput))
                .expectNext(cashoutOutput)
                .verifyComplete();
    }

    @Test
    void getCashoutByUserId() {
        Long userId = 1L;
        Cashout cashout = new Cashout(1L, 1L, 100.00);
        Cashout cashout2 = new Cashout(2L, 1L, 100.00);

        Mockito.when(cashoutRepository.findByUserId(anyLong())).thenReturn(Flux.just(cashout, cashout2));

        StepVerifier.create(cashoutService.getCashoutByUserId(userId))
                .expectNext(cashout)
                .expectNext(cashout2)
                .verifyComplete();
    }
}