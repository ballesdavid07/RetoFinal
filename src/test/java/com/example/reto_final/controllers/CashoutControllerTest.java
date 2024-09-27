package com.example.reto_final.controllers;

import com.example.reto_final.domain.Cashout;
import com.example.reto_final.domain.User;
import com.example.reto_final.services.interfaces.ICashoutService;
import com.example.reto_final.services.interfaces.IRestClient;
import com.example.reto_final.services.interfaces.IUserService;
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
import static org.mockito.ArgumentMatchers.*;

class CashoutControllerTest {

    @Mock
    IUserService userService;

    @Mock
    ICashoutService cashoutService;

    @Mock
    IRestClient restClient;

    @InjectMocks
    CashoutController cashoutController;

    @BeforeEach
    public void setup(){
        MockitoAnnotations.openMocks(this);
    }


    @Test
    void createCashout() {
        Cashout cashoutInput = new Cashout(null, 1L, 80.00);
        Cashout cashoutOutput = new Cashout(1L, 1L, 80.00);

        User userOutput = new User(1L, "test", 100.00);
        User userOutputUpdated = new User(1L, "test", 20.00);

        Mockito.when(userService.getUserById(anyLong())).thenReturn(Mono.just(userOutput));
        Mockito.when(restClient.validatePayment(any())).thenReturn(Mono.just("Approved"));
        Mockito.when(userService.updateBalance(anyLong(), anyDouble())).thenReturn(Mono.just(userOutputUpdated));
        Mockito.when(cashoutService.createCashout(any())).thenReturn(Mono.just(cashoutOutput));


        StepVerifier.create(cashoutController.createCashout(cashoutInput))
                .expectNext(cashoutOutput)
                .verifyComplete();
    }

    @Test
    void createWrongCashout() {
        Cashout cashoutInput = new Cashout(null, 1L, 80.00);
        User userOutput = new User(1L, "test", 60.00);

        Mockito.when(userService.getUserById(anyLong())).thenReturn(Mono.just(userOutput));

        StepVerifier.create(cashoutController.createCashout(cashoutInput))
                .expectErrorMatches(throwable -> throwable.getMessage().equals("Balance insuficiente."))
                .verify();
    }

    @Test
    void createNotApprovedCashout() {
        Cashout cashoutInput = new Cashout(null, 1L, 80.00);
        User userOutput = new User(1L, "test", 100.00);

        Mockito.when(userService.getUserById(anyLong())).thenReturn(Mono.just(userOutput));
        Mockito.when(restClient.validatePayment(any())).thenReturn(Mono.just("Not Approved"));

        StepVerifier.create(cashoutController.createCashout(cashoutInput))
                .expectErrorMatches(throwable -> throwable.getMessage().equals("Payment failed."))
                .verify();
    }

    @Test
    void getByUserId() {
        Long userId = 1L;
        Cashout cashoutOutput = new Cashout(1L, 1L, 80.00);
        Cashout cashoutOutput2 = new Cashout(2L, 1L, 70.00);

        Mockito.when(restClient.getHistoryByUserId(anyLong())).thenReturn(Flux.just(cashoutOutput, cashoutOutput2));

        StepVerifier.create(cashoutController.getByUserId(userId))
                .expectNext(cashoutOutput)
                .expectNext(cashoutOutput2)
                .verifyComplete();
    }
}