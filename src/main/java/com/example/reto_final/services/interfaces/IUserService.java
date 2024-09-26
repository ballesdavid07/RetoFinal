package com.example.reto_final.services.interfaces;

import com.example.reto_final.domain.User;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IUserService {
    public Flux<User> getAllUsers();

    public Mono<User> getUserById(Long id);

    public Mono<User> createUser(User user);

    public Mono<User> updateBalance(Long id, Double balance);
}
