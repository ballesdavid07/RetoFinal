package com.example.reto_final.controllers;

import com.example.reto_final.domain.Cashout;
import com.example.reto_final.services.interfaces.ICashoutService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;

import static org.mockito.ArgumentMatchers.anyLong;

@ContextConfiguration(classes = {TransactionHistoryController.class, GlobalHandleError.class})
@WebFluxTest(TransactionHistoryController.class)
class TransactionHistoryControllerTest {

    @Autowired
    WebTestClient webTestClient;

    @MockBean
    ICashoutService cashoutService;

    @Test
    void getByUserId() {
        Long userId = 1L;
        Cashout cashoutOutput = new Cashout(1L, 1L, 200.00);
        Cashout cashoutOutput2 = new Cashout(2L, 1L, 300.00);

        Mockito.when(cashoutService.getCashoutByUserId(anyLong())).thenReturn(Flux.just(cashoutOutput, cashoutOutput2));

        webTestClient
                .get()
                .uri("/transaction-history/user/{userId}", userId)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$[0].amount").isEqualTo(200.00);
    }
}