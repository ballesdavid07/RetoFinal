package com.example.reto_final.controllers;

import com.example.reto_final.controllers.dto.Balance;
import com.example.reto_final.domain.User;
import com.example.reto_final.services.interfaces.IUserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private IUserService userService;

    @GetMapping("/{id}")
    public Mono<User> getById(@PathVariable Long id){
        return this.userService.getUserById(id);
    }

    @GetMapping("/all")
    public Flux<User> getById(){
        return this.userService.getAllUsers();
    }

    @PostMapping
    public Mono<User> create(@Valid @RequestBody User user){
        return this.userService.createUser(user);
    }

    @PutMapping("/{id}/balance")
    public Mono<User> updateBalance(@PathVariable Long id, @RequestBody Balance balance){
        return this.userService.updateBalance(id, balance.getBalance());
    }
}
