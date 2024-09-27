package com.example.reto_final.controllers;

import com.example.reto_final.domain.Cashout;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.reactive.server.WebTestClient;


@ContextConfiguration(classes = {PaymentController.class, GlobalHandleError.class})
@WebFluxTest(PaymentController.class)
class PaymentControllerTest {

    @Autowired
    WebTestClient webTestClient;

    @Test
    void validatePayment() {
        Cashout cashoutInput = new Cashout(null, 1L, 80.00);

        webTestClient
                .post()
                .uri("/payments")
                .bodyValue(cashoutInput)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$").isEqualTo("Approved")
                .returnResult();
    }

    @Test
    void validateNotApprovedPayment() {
        Cashout cashoutInput = new Cashout(null, 1L, 40.00);

        webTestClient
                .post()
                .uri("/payments")
                .bodyValue(cashoutInput)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$").isEqualTo("Not approved")
                .returnResult();
    }
}