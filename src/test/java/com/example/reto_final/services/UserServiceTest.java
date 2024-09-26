package com.example.reto_final.services;

import com.example.reto_final.domain.User;
import com.example.reto_final.domain.repositories.UserRepository;
import com.example.reto_final.exceptions.UserNotFoundException;
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

class UserServiceTest {

    @Mock
    UserRepository userRepository;

    @InjectMocks
    UserService userService;

    @BeforeEach
    public void setup(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllUsersTest(){
        User user = new User(1L, "test", 300.00);
        Mockito.when(userRepository.findAll()).thenReturn(Flux.just(user));

        StepVerifier.create(userService.getAllUsers())
                .expectNextMatches(u -> u.getName().equals("test"))
                .verifyComplete();
    }

    @Test
    void getUserByIdTest(){
        Long id = 1L;
        User user = new User(1L, "test", 300.00);
        Mockito.when(userRepository.findById(anyLong())).thenReturn(Mono.just(user));

        StepVerifier.create(userService.getUserById(id))
                .expectNextMatches(u -> u.getBalance() == 300.00)
                .verifyComplete();
    }

    @Test
    void getUserByIdWrongTest(){
        Long id = 1L;
        Mockito.when(userRepository.findById(anyLong())).thenReturn(Mono.empty());

        StepVerifier.create(userService.getUserById(id))
                .expectError(UserNotFoundException.class)
                .verify();
    }

    @Test
    void createUserTest(){
        User userOutput = new User(1L, "test", 300.00);
        User userInput = new User(null, "test", 300.00);
        Mockito.when(userRepository.save(any())).thenReturn(Mono.just(userOutput));

        StepVerifier.create(userService.createUser(userInput))
                .expectNext(userOutput)
                .verifyComplete();
    }

    @Test
    void updateUserTest(){
        Long id = 1L;
        Double balance = 100.00;
        User user = new User(1L, "test", 300.00);
        User userUpdated = new User(1L, "test", 400.00);

        Mockito.when(userRepository.findById(anyLong())).thenReturn(Mono.just(user));
        Mockito.when(userRepository.save(any())).thenReturn(Mono.just(userUpdated));

        StepVerifier.create(userService.updateBalance(id, balance))
                .expectNext(userUpdated)
                .verifyComplete();
    }

}