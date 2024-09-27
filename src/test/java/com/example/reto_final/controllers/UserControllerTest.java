package com.example.reto_final.controllers;

import com.example.reto_final.controllers.dto.Balance;
import com.example.reto_final.domain.User;
import com.example.reto_final.services.interfaces.IUserService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.mockito.ArgumentMatchers.*;

@ContextConfiguration(classes = {UserController.class, GlobalHandleError.class})
@WebFluxTest(UserController.class)
class UserControllerTest {

    @Autowired
    WebTestClient webTestClient;

    @MockBean
    IUserService userService;

    @Test
    void getById() {
        Long id = 1L;
        User userOutput = new User(1L, "test", 200.00);
        Mockito.when(userService.getUserById(anyLong())).thenReturn(Mono.just(userOutput));

        webTestClient
                .get()
                .uri("/users/{id}", id)
                .exchange()
                .expectStatus().isOk()
                .expectBody(User.class)
                .returnResult()
                .getResponseBody();
    }

    @Test
    void testGetAll() {
        User userOutput = new User(1L, "test", 200.00);
        User userOutput2 = new User(2L, "test2", 200.00);

        Mockito.when(userService.getAllUsers()).thenReturn(Flux.just(userOutput, userOutput2));

        webTestClient
                .get()
                .uri("/users/all")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$[0].name").isEqualTo("test")
                .jsonPath("$[1].name").isEqualTo("test2");;
    }

    @Test
    void create() {
        User userInput = new User(null, "test", 200.00);
        User userOutput = new User(1L, "test", 200.00);

        Mockito.when(userService.createUser(any())).thenReturn(Mono.just(userOutput));

        webTestClient
                .post()
                .uri("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(userInput)
                .exchange()
                .expectStatus().isOk()
                .expectBody(User.class)
                .returnResult();
    }

    @Test
    void updateBalance() {
        Long id = 1L;
        Balance balance = new Balance(100.00);
        User userOutput = new User(1L, "test", 100.00);

        Mockito.when(userService.updateBalance(anyLong(), anyDouble())).thenReturn(Mono.just(userOutput));

        webTestClient
                .put()
                .uri("/users/{id}/balance", id)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(balance)
                .exchange()
                .expectStatus().isOk()
                .expectBody(User.class)
                .returnResult();
    }
}